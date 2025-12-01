package com.example.coffeevibe.model

import androidx.compose.runtime.Stable

@Stable
data class MenuItem(
    val id: Int = 0,
    val name: String = "",
    val price: Int = 0,
    val image: String = "",
    val description: String = "",
    val category: String = "",
    val status: String = ""
)
