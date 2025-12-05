package com.example.coffeevibe.model

import com.google.firebase.Timestamp

data class OrderManagerItem(
    val id: Int = 0,
    val clientName: String,
    val pickupTime: Timestamp,
    val state: String,
    val totalPrice: Int,
    val orderItems: MutableList<OrderManagerOrderItem>
)
