package com.example.coffeevibe.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
class ProductAdmin {
    var id by mutableIntStateOf(0)
    var name by mutableStateOf("")
    var price by mutableIntStateOf(0)
    var discountPrice by mutableIntStateOf(0)
    var image by mutableStateOf("")
    var description by mutableStateOf("")
    var category by mutableStateOf("")
    var composition by mutableStateOf("")
    var status by mutableStateOf("")
    var kbju by mutableStateOf("")
}