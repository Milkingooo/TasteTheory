package com.example.coffeevibe.model

import androidx.compose.runtime.Stable

@Stable
data class MenuItem(
    val id: Int = 0,
    var name: String = "",
    var price: Int = 0,
    var discountPrice: Int = 0,
    val image: String = "",
    var description: String = "",
    var category: String = "",
    var composition: String = "",
    var status: String = "",
    var kbju: String = ""
)
