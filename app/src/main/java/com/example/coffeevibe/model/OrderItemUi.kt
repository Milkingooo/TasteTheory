package com.example.coffeevibe.model

data class OrderItemUi(
    val id: Int = 0,
    val idItem: Int,
    val name: String,
    val price: Int,
    val image: String,
    var quantity: Int = 1
)
