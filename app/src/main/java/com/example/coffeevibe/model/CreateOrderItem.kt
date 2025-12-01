package com.example.coffeevibe.model

data class CreateOrderItem(
    val price: Int,
    val number: String,
    val pickupTime: String,
)