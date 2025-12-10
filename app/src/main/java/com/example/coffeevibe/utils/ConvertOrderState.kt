package com.example.coffeevibe.utils

object ConvertOrderState {
    fun convertOrderStateToString(state: Int): String = when (state) {
        1 -> "Создан"
        2 -> "Готовится"
        3 -> "Готов"
        4 -> "Выдан"
        else -> "Ошибка"
    }
}