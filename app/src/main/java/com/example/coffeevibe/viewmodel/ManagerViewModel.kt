package com.example.coffeevibe.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeevibe.model.CreateOrderItem
import com.example.coffeevibe.model.OrderManagerItem
import com.example.coffeevibe.model.OrderManagerOrderItem
import com.example.coffeevibe.utils.AuthUtils
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ManagerViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    private var _isOrdersLoad = MutableStateFlow(false)
    var isOrdersLoad: StateFlow<Boolean> = _isOrdersLoad

    private val _ordersList = MutableStateFlow<List<OrderManagerItem>>(emptyList())
    val ordersList: StateFlow<List<OrderManagerItem>> = _ordersList.asStateFlow()

    private var ordersListener: ListenerRegistration? = null
    private var itemsListener: ListenerRegistration? = null

    private var currentOrders: List<DocumentSnapshot> = emptyList()
    private var currentOrderItems: List<DocumentSnapshot> = emptyList()

    init {
        //loadManagerOrders(1)
        observeOrders(1)
    }

    fun loadManagerOrders(locationId: Int) {
        //loadOrdersByLocation(locationId)
    }

    fun observeOrders(locationId: Int) {
        _isOrdersLoad.value = true

        // --- 1. LISTENER на таблицу Order ---
        ordersListener = firestore.collection("Order")
            .whereEqualTo("IdLocation", locationId)
            .addSnapshotListener { snapshot, e ->
                currentOrders = snapshot?.documents ?: emptyList()
                recombine()
            }

        // --- 2. LISTENER на таблицу OrderItem ---
        itemsListener = firestore.collection("OrderItem")
            .addSnapshotListener { snapshot, e ->
                currentOrderItems = snapshot?.documents ?: emptyList()
                recombine()
            }
    }

     //--- Сборка результата ---
     private fun recombine() {
         viewModelScope.launch(Dispatchers.Main) {

             // фильтруем только активные заказы
             val orders = currentOrders.filter {
                 it.getString("Status") != "Выдан"
             }

             // группируем items по IdOrder
             val grouped = currentOrderItems.groupBy {
                 it.getLong("IdOrder")?.toInt()
             }

             val result = orders.map { orderDoc ->

                 val idOrder = orderDoc.id.toIntOrNull()

                 val items = grouped[idOrder].orEmpty().map { itemDoc ->
                     OrderManagerOrderItem(
                         idGood = itemDoc.getLong("IdGood")?.toInt() ?: 0,
                         name = getItemNameById(itemDoc.getLong("IdGood")?.toInt()),
                         quantity = itemDoc.getLong("Quantity")?.toInt() ?: 0
                     )
                 }

                 OrderManagerItem(
                     id = idOrder ?: 0,
                     clientName = orderDoc.getString("IdClient") ?: "",
                     pickupTime = (orderDoc.get("PickupTime") ?: "") as Timestamp,
                     state = orderDoc.getLong("Status")?.toInt() ?: 0,
                     totalPrice = orderDoc.getLong("TotalPrice")?.toInt() ?: 0,
                     orderItems = items.toMutableList()
                 )
             }

             withContext(Dispatchers.Main) {
                 _ordersList.value = result
                 _isOrdersLoad.value = false
             }
         }
     }

    fun updateOrderState(orderId: Int, state: Int) {
        viewModelScope.launch {
            firestore.collection("Order").document(orderId.toString()).update("Status", state)
                .await()
            recombine()
        }
    }

    // --- Отключение листенеров ---
    override fun onCleared() {
        super.onCleared()
        ordersListener?.remove()
        itemsListener?.remove()
    }

    private suspend fun getItemNameById(id: Any?): String{
        val snapshot = firestore.collection("Good").whereEqualTo("Id", id).get().await()
        val itemName = snapshot.documents.firstOrNull()?.data?.get("Name").toString()
        return itemName
    }
}

