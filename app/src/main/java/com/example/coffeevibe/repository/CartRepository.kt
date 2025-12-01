package com.example.coffeevibe.repository

import androidx.lifecycle.asLiveData
import com.example.coffeevibe.database.CartDao
import com.example.coffeevibe.database.CartEntity

open class CartRepository(private val cartDao: CartDao){

    fun getAllItems() = cartDao.getAllItems()

    suspend fun addItem(password: CartEntity) = cartDao.insertItem(password)

    suspend fun deleteItemById(id: Int) = cartDao.deleteItemById(id)

    suspend fun updateItem(password: CartEntity) = cartDao.updateItem(password)

    suspend fun deleteAllItems() = cartDao.deleteAllItems()

}