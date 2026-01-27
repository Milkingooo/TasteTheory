package com.example.coffeevibe.utils

object ConvertOrderState {
    fun convertOrderStateToString(state: Int): String = when (state) {
        1 -> "создан"
        2 -> "готовится"
        3 -> "готов"
        4 -> "выдан"
        5 -> "отменен"
        else -> "ошибка"
    }
}