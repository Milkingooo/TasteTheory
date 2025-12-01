package com.example.coffeevibe.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartItems")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idItem: Int,
    val name: String,
    val price: Int,
    val image: String,
    var quantity: Int = 1,
)

data class MenuItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idItem: Int,
    val name: String,
    val price: Int,
    val image: String,
    val description: String,
    val category: String,
    val status: String
)
