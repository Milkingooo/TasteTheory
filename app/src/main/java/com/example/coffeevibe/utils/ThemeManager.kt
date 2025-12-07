package com.example.coffeevibe.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ThemeManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "ThemePrefs"
        private const val KEY_IS_DARK_THEME = "isDarkTheme"
    }

    // Получаем экземпляр SharedPreferences
    private val prefs: SharedPreferences by lazy { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    /**
     * Устанавливает выбранную тему (true - тёмная, false - светлая)
     */
    fun setIsDarkTheme(isDarkTheme: Boolean) {
        prefs.edit {
            putBoolean(KEY_IS_DARK_THEME, isDarkTheme)
        }
    }

    /**
     * Возвращает true, если выбрана тёмная тема, иначе возвращает false (светлая тема)
     */
    fun isDarkTheme(): Boolean {
        return prefs.getBoolean(KEY_IS_DARK_THEME, false) // По умолчанию возвращаем light theme
    }
}