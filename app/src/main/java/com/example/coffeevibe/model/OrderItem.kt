package com.example.coffeevibe.model

data class OrderItem(
    val id: Int,
    var quantity: Int = 1,
    val name: String,
    val price: Int,
    val image: String
)
