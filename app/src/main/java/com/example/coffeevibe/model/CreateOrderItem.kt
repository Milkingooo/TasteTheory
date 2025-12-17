package com.example.coffeevibe.model

data class CreateOrderItem(
    val id: Int,
    val price: Int,
    val number: String,
    val pickupTime: String,
    val state: Int,
    val date: String
)