package com.example.coffeevibe.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.database.CartEntity
import com.example.coffeevibe.database.FirebaseDao
import com.example.coffeevibe.model.CreateOrderItem
import com.example.coffeevibe.model.Location
import com.example.coffeevibe.model.MenuItem
import com.example.coffeevibe.repository.CartRepository
import com.example.coffeevibe.utils.AuthUtils
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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



    init {
        loadData()
        isUserSingleOrder()
        getOrderNumAndPrice()
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = firestore.collection("Good").get().await()
                val items = snapshot.documents.mapNotNull { item ->
                    try {
                        MenuItem(
                            id = item.data?.get("Id").toString().toInt(),
                            name = item.data?.get("Name").toString(),
                            price = item.data?.get("Price").toString().toInt(),
                            category = item.data?.get("Category").toString(),
                            description = item.data?.get("Description").toString(),
                            image = item.data?.get("Image").toString(),
                            status = item.data?.get("Status").toString(),
                        )
                    } catch (e: Exception) {
                        Log.e("MyViewModel", "Error loading data", e)
                        null
                    }
                }
                Log.d("MyViewModel", "Loaded data: $items")
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
                    document["Status"]?.toString()?.trim() == "Создан"
                }

                _isOrderHas.value = ordersCount > 0
            } catch (e: Exception) {
                Log.e("MyViewModel", "Error loading data", e)
                _isOrderHas.value = false
            }
        }
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
                        item.data?.get("Status").toString() != "Выдан" -> {
                         CreateOrderItem(
                             price = item.data?.get("TotalPrice").toString().toInt(),
                             number = item.id,
                             pickupTime = item.data?.get("PickupTime").toString(),
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


}