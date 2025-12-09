package com.example.coffeevibe.model

data class UserOrder(
    val price: Int,
    val number: String,
    val pickupTime: String,
    val date: String,
    val location: String,
    val state: Int
)
