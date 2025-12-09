package com.example.coffeevibe.utils

object ConvertOrderState {
    fun convertOrderStateToString(state: Int): String = when (state) {
        0 -> "Создан"
        1 -> "Готовится"
        2 -> "Готов"
        3 -> "Выдан"
        else -> "Создан"
    }
}