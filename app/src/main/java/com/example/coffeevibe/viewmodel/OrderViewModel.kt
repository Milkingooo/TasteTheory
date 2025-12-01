package com.example.coffeevibe.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.database.CartEntity
import com.example.coffeevibe.model.OrderItem
import com.example.coffeevibe.model.OrderItemUi
import com.example.coffeevibe.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(private val repository: CartRepository, context: Context) : ViewModel() {

    private val menuVm = MenuViewModel(context)
    private val db = CartDatabase.getDatabase(Application())
    private val cartDao = db.cartDao()

    private val _itemList = MutableStateFlow<List<CartEntity>>(emptyList())
    val itemList: StateFlow<List<CartEntity>> = _itemList

    private val _total = MutableStateFlow(0)
    val total: StateFlow<Int> = _total

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            try {
                repository.getAllItems().collect { entities ->
                    _itemList.value = entities
                    _total.value = entities.sumOf { it.price * it.quantity }
                }
            } catch (e: Exception) {
                Log.e("OrderListViewModel", "Error getting cart items: ${e.message}", e)
            }
        }
    }

    fun addItem(id: Int, name: String, price: Int, quantity: Int, image: String) {
        if (_itemList.value.none { it.idItem == id }) {
            viewModelScope.launch {
                try {
                    val newItem = CartEntity(
                        idItem = id,
                        name = name,
                        price = price,
                        quantity = quantity,
                        image = image
                    )
                    repository.addItem(newItem)
                    menuVm.loadData()
                } catch (e: Exception) {
                    Log.e("OrderListViewModel", "Error adding password: ${e.message}", e)
                }
            }
        }
    }

    fun deleteItem(item: CartEntity) {
        viewModelScope.launch {
            try {
                cartDao.deleteItem(item)
                menuVm.loadData()
            } catch (e: Exception) {
                Log.e("OrderListViewModel", "Error deleting item: ${e.message}", e)
            }
        }
    }

    fun isCartIsEmpty(): Boolean {
        return itemList.value.isEmpty()
    }

    fun deleteItemById(id: Int) {
        viewModelScope.launch {
            try {
                cartDao.deleteItemById(id)
                Log.d("OrderListViewModel", "Item deleted: $id")
            } catch (e: Exception) {
                Log.e("OrderListViewModel", "Error deleting item: ${e.message}", e)
            }
        }
    }

    fun isItemInCart(id: Int): Boolean {
        return itemList.value.any { it.idItem == id }
    }

    fun deleteAllItems() {
        viewModelScope.launch {
            try {
                cartDao.deleteAllItems()
            } catch (e: Exception) {
                Log.e("OrderListViewModel", "Error deleting all items: ${e.message}", e)
            }
        }
    }

    fun updateItem(item: CartEntity, newQuantity: Int) {
        viewModelScope.launch {
            try {
                if (newQuantity in 1..10) {
                    cartDao.updateItem(item.copy(quantity = newQuantity))
                    loadCartItems()
                }
                else {
                    cartDao.deleteItem(item)
                }
            } catch (e: Exception) {
                Log.e("OrderListViewModel", "Error updating item: ${e.message}", e)
            }
        }
    }

}