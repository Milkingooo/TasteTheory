package com.example.coffeevibe.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
    DYNAMIC
}

object ThemeState {
    var currentTheme: ThemeMode by mutableStateOf(ThemeMode.SYSTEM)
}
