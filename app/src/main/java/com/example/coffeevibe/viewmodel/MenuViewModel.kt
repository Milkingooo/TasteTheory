package com.example.coffeevibe.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeevibe.model.CreateOrderItem
import com.example.coffeevibe.model.Location
import com.example.coffeevibe.model.MenuItem
import com.example.coffeevibe.model.OrderManagerOrderItem
import com.example.coffeevibe.model.ProductAdmin
import com.example.coffeevibe.model.UserOrder
import com.example.coffeevibe.utils.AuthUtils
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MenuViewModel(val context: Context) : ViewModel() {
    private val firestore = Firebase.firestore

    private val _dataList = MutableStateFlow<List<MenuItem>>(emptyList())
    val dataList: StateFlow<List<MenuItem>> = _dataList.asStateFlow()

    private val _isOrderHas = MutableStateFlow(false)
    val isOrderHas: StateFlow<Boolean> = _isOrderHas

    private val _orderNP = MutableStateFlow<List<CreateOrderItem>>(emptyList())
    val orderNP: StateFlow<List<CreateOrderItem>> = _orderNP.asStateFlow()

    private var _isOrderWas = MutableStateFlow(false)
    var isOrderWas: StateFlow<Boolean> = _isOrderWas

    private var _isOrdersLoad = MutableStateFlow(false)
    var isOrdersLoad: StateFlow<Boolean> = _isOrdersLoad

    private var _isMenuLoad = MutableStateFlow(false)
    var isMenuLoad: StateFlow<Boolean> = _isMenuLoad

    private val _userOrders = MutableStateFlow<List<UserOrder>>(emptyList())
    val userOrders: StateFlow<List<UserOrder>> = _userOrders.asStateFlow()

    private var listenerRegistration: ListenerRegistration? = null



    init {
        isUserSingleOrder()
        getOrderNumAndPrice()
        subscribeToOrders()
        subscribeToMenu()
        getUserOrders()
    }

    fun updateOrderWas(state: Boolean) {
        _isOrderWas.value = state
    }


    private fun subscribeToOrders() {
        listenerRegistration = firestore
            .collection("Order")
            .whereEqualTo("IdClient", AuthUtils.getUserId())
            .addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("MyViewModel", "Error loading orders", error)
                return@addSnapshotListener
            }
                val items = snapshot?.documents?.mapNotNull { item ->
                    try {
                        when {
                            item.data?.get("Status").toString().toInt() != 4 && item.data?.get("Status").toString().toInt() != 5 -> {
                                CreateOrderItem(
                                    price = item.data?.get("TotalPrice").toString().toInt(),
                                    number = item.id,
                                    pickupTime = item.data?.get("PickupTime").toString(),
                                    state = item.data?.get("Status").toString().toInt(),
                                    id = item.id.toInt(),
                                    date = item.data?.get("Date").toString(),
                                )
                            }
                            else -> {
                                null
                            }
                        }

                    } catch (e: Exception) {
                        Log.e("MyViewModel", "Error loading data", e)
                        null
                    }
                }
                if (items != null) {
                    _orderNP.value = items
                    isUserSingleOrder()
                }
                else {
                    _orderNP.value = emptyList()
                }
        }
    }

    private fun subscribeToMenu() {
        _isMenuLoad.value = true
        listenerRegistration = firestore
            .collection("Good")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("MyViewModel", "Error loading menu", error)
                    return@addSnapshotListener
                }
                val items = snapshot?.documents?.mapNotNull { item ->
                    try {
                        MenuItem(
                            id = item.data?.get("Id").toString().toInt(),
                            name = item.data?.get("Name").toString(),
                            price = item.data?.get("Price").toString().toInt(),
                            discountPrice = item.data?.get("DiscountPrice").toString().toInt(),
                            category = item.data?.get("Category").toString(),
                            description = item.data?.get("Description").toString(),
                            image = item.data?.get("Image").toString(),
                            status = item.data?.get("Status").toString(),
                            composition = item.data?.get("Dairy").toString(),
                            kbju = item.data?.get("KBJU").toString()
                        )
                    } catch (e: Exception) {
                        Log.e("MyViewModel", "Error loading data", e)
                        null
                    }
                }
                Log.d("MyViewModel", "Loaded data: $items")

                _isMenuLoad.value = false

                if (items != null && (_dataList.value.size != items.size || _dataList.value.map { it.id } != items.map { it.id })) {
                    _dataList.value = items
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }

    fun deleteAllOrdersBeforeToday() {
        val today = Timestamp.now()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val orders = firestore.collection("Order")
                    .whereLessThan("PickupTime", today)
                    .get()
                    .await()

                val batch = firestore.batch()

                Log.d("MyViewModel", orders.toString())
                for (orderDoc in orders.documents) {

                    val orderItems = firestore.collection("OrderItem")
                        .whereEqualTo("IdOrder", orderDoc.id)
                        .get()
                        .await()

                    for (itemDoc in orderItems.documents) {
                        batch.delete(itemDoc.reference)
                    }

                    batch.delete(orderDoc.reference)
                }

                // Коммитим всё одним запросом
                batch.commit().await()

                withContext(Dispatchers.Main) {
                    loadOrders()
                    loadUserOrders()
                }

                Log.d("MyViewModel", "Batch delete completed")

            } catch (e: Exception) {
                Log.e("MyViewModel", "Batch delete error", e)
            }
        }
    }

    private fun loadData() {
        _isMenuLoad.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = firestore.collection("Good")
                    .get()
                    .await()
                val items = snapshot.documents.mapNotNull { item ->
                    try {
                        MenuItem(
                            id = item.data?.get("Id").toString().toInt(),
                            name = item.data?.get("Name").toString(),
                            price = item.data?.get("Price").toString().toInt(),
                            discountPrice = item.data?.get("DiscountPrice").toString().toInt(),
                            category = item.data?.get("Category").toString(),
                            description = item.data?.get("Description").toString(),
                            image = item.data?.get("Image").toString(),
                            status = item.data?.get("Status").toString(),
                            composition = item.data?.get("Dairy").toString(),
                            kbju = item.data?.get("KBJU").toString()

                        )
                    } catch (e: Exception) {
                        Log.e("MyViewModel", "Error loading data", e)
                        null
                    }
                }
                Log.d("MyViewModel", "Loaded data: $items")
                _isMenuLoad.value = false
                withContext(Dispatchers.Main) {
                    if (!_dataList.value.containsAll(items) || _dataList.value.size != items.size) {
                        _dataList.value = items
                    }
                }
                Log.d("MyViewModel", "Loaded data: $items")
            } catch (e: Exception) {
                Log.e("MyViewModel", "Error loading data", e)
            }
        }
    }

    private fun isUserSingleOrder() {
        viewModelScope.launch {
            try {
                val snapshot = firestore
                    .collection("Order")
                    .whereEqualTo("IdClient", AuthUtils.getUserId())
                    .get()
                    .await()

                val ordersCount = snapshot.documents.count { document ->
                    document["Status"].toString().toInt() != 4 && document["Status"].toString().toInt() != 5
                }
                Log.e("MyViewModel", "Orders count:  $ordersCount")
                _isOrderHas.value = ordersCount > 0
            } catch (e: Exception) {
                Log.e("MyViewModel", "Error loading data", e)
                _isOrderHas.value = false
            }
        }
    }

    fun loadOrders() {
        isUserSingleOrder()
        getOrderNumAndPrice()
    }

    fun loadMenu() {
        loadData()
    }

    private fun getOrderNumAndPrice() {
        viewModelScope.launch {
            val snapshot = firestore
                .collection("Order")
                .whereEqualTo("IdClient", AuthUtils.getUserId())
                .get()
                .await()
            val items = snapshot.documents.mapNotNull { item ->
                try {
                    when {
                        item.data?.get("Status").toString().toInt() != 4 && item.data?.get("Status").toString().toInt() != 5 -> {
                         CreateOrderItem(
                             price = item.data?.get("TotalPrice").toString().toInt(),
                             number = item.id,
                             pickupTime = item.data?.get("PickupTime").toString(),
                             state = item.data?.get("Status").toString().toInt(),
                             id = item.id.toInt(),
                             date = item.data?.get("Date").toString()
                         )
                        }
                        else -> {
                            null
                        }
                    }

                } catch (e: Exception) {
                    Log.e("MyViewModel", "Error loading data", e)
                    null
                }
            }
            withContext(Dispatchers.Main){
                _orderNP.value = items
            }
        }
    }

    fun getLocations(locations: (List<Location>) -> Unit) {
        val loc: MutableList<Location> = mutableListOf()

        viewModelScope.launch {
            val snapshot = firestore.collection("Location").get().await()
            snapshot.documents.mapNotNull { item ->
                try {
                    loc.add(
                        Location(
                            id = item.data?.get("Id").toString().toInt(),
                            address = item.data?.get("Address").toString()
                        )
                    )
                } catch (e: Exception) {
                    Log.e("MyViewModel", "Error loading data", e)
                    null
                }

            }
        }
        locations(loc)
    }

    fun loadUserOrders() {
        getUserOrders()
    }
    private fun getUserOrders() {
        _isOrdersLoad.value = true

        viewModelScope.launch {
            val snapshot = firestore
                .collection("Order")
                .whereEqualTo("IdClient", AuthUtils.getUserId())
                .whereIn("Status", listOf(4, 5))
                .get()
                .await()
            val items = snapshot.documents.mapNotNull { item ->
                try {
                    UserOrder(
                        price = item.data?.get("TotalPrice").toString().toInt(),
                        number = item.id,
                        pickupTime = item.data?.get("PickupTime").toString(),
                        state = item.data?.get("Status").toString().toInt(),
                        location = getLocationNameById(item.data?.get("IdLocation")),
                        date = item.data?.get("Date").toString()
                    )
                } catch (e: Exception) {
                    Log.e("MyViewModel", "Error loading user orders", e)
                    null
                }
            }
            withContext(Dispatchers.Main){
                _isOrdersLoad.value = false
                _userOrders.value = items
            }
        }
    }

    suspend fun getLocationNameById(id: Any?): String {
        val snapshot = firestore.collection("Location").whereEqualTo("Id", id).get().await()
            val locationName = snapshot.documents.firstOrNull()?.data?.get("Address").toString()
            return locationName
    }

    fun cancelOrder(orderId: Int) {
        viewModelScope.launch {
            try {
                firestore.collection("Order").document(orderId.toString()).update("Status", 5).await()
                Log.d("MyViewModel", "Order cancelled successfully")
            } catch (e: Exception) { }
        }
    }

    //Управление позициями в админке

    fun updateProductById(id: Int, newProduct: ProductAdmin) {
        viewModelScope.launch {
            val products = firestore
                .collection("Good")
                .whereEqualTo("Id", id)
                .get()
                .await()

            val pr: MutableMap<String?, Any> =  mutableMapOf()
            pr["Name"] = newProduct.name
            pr["Description"] = newProduct.description
            pr["Price"] = newProduct.price.toString()
            pr["DiscountPrice"] = newProduct.discountPrice.toString()
            pr["Category"] = newProduct.category
            pr["Dairy"] = newProduct.composition
            pr["Image"] = newProduct.image
            pr["Status"] = newProduct.status
            pr["KBJU"] = newProduct.kbju

            try {
                firestore
                    .collection("Good")
                    .document(extractDocumentId(products.documents.toString()))
                    .update(pr)
                    .addOnSuccessListener {
                        Toast.makeText(context,"Данные успешно сохранены", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context,"Ошибка! Данные не сохранены", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: Exception) {
                Toast.makeText(context,"Ошибка! Данные не сохранены", Toast.LENGTH_SHORT).show()
                Log.e("SaveNewProduct", "Error: ${e.stackTrace}")

            }
        }
    }
    //[DocumentSnapshot{key=Good/1YquY7aOiGoCtrdWyBaZ, metadata=SnapshotMetadata{hasPendingWrites=false, isFromCache=false}
    fun extractDocumentId(doc: String) : String{
        return doc.split('/')[1].split(',')[0]
    }
    fun getProductInfoById(id: Int?, callback: (ProductAdmin) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = firestore.collection("Good")
                    .whereEqualTo("Id", id)
                    .get()
                    .await()

                val prod = ProductAdmin()

                val product = snapshot.documents.firstNotNullOf { item ->
                    try {
                        prod.id = item.data?.get("Id").toString().toInt()
                        prod.name = item.data?.get("Name").toString()
                        prod.price = item.data?.get("Price").toString().toInt()
                        prod.discountPrice = item.data?.get("DiscountPrice").toString().toInt()
                        prod.category = item.data?.get("Category").toString()
                        prod.description = item.data?.get("Description").toString()
                        prod.image = item.data?.get("Image").toString()
                        prod.status = item.data?.get("Status").toString()
                        prod.composition = item.data?.get("Dairy").toString()
                        prod.kbju = item.data?.get("KBJU").toString()

                    } catch (e: Exception) {
                        Log.e("GetProduct", "Error loading data", e)
                        null
                    }
                }
                Log.e("GetProduct", product.toString())
                callback(prod)
            } catch (e: Exception) {
                Log.e("GetProduct", "Error loading data", e)
            }
        }
    }

    fun getOrderDetails(id: Int, callback: (List<OrderManagerOrderItem>) -> Unit, isLoading: (Boolean) -> Unit) {
        isLoading(true)
        Log.d("GetDetails", "ID: $id")

        viewModelScope.launch {
            val snapshot = firestore
                .collection("OrderItem")
                .whereEqualTo("IdOrder", id)
                .get()
                .await()

            val items = snapshot.documents.map { itemDoc ->
                OrderManagerOrderItem(
                    idGood = itemDoc.getLong("IdGood")?.toInt() ?: 0,
                    name = getItemNameById(itemDoc.getLong("IdGood")?.toInt()),
                    quantity = itemDoc.getLong("Quantity")?.toInt() ?: 0
                )
            }
            Log.d("GetDetails", items.toString())
            isLoading(false)
            callback(items)
        }
    }

    private suspend fun getItemNameById(id: Any?): String{
        val snapshot = firestore.collection("Good").whereEqualTo("Id", id).get().await()
        val itemName = snapshot.documents.firstOrNull()?.data?.get("Name").toString()
        return itemName
    }
}