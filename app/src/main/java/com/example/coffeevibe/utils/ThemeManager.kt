package com.example.coffeevibe.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ThemeManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "ThemePrefs"
        private const val KEY_IS_DARK_THEME = "isDarkTheme"
        private const val KEY_IS_SYSTEM_THEME = "isSystemTheme"
        private const val KEY_IS_DYNAMIC_THEME = "isDynamicTheme"
    }

    private val prefs: SharedPreferences by lazy { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    fun setIsDarkTheme(isDarkTheme: Boolean) {
        prefs.edit {
            putBoolean(KEY_IS_DARK_THEME, isDarkTheme)
        }
    }

    fun setIsSystemTheme(isSystemTheme: Boolean) {
        prefs.edit {
            putBoolean(KEY_IS_SYSTEM_THEME, isSystemTheme)
        }
    }

    fun setIsDynamicTheme(isDynamicTheme: Boolean) {
        prefs.edit {
            putBoolean(KEY_IS_DYNAMIC_THEME, isDynamicTheme)
        }
    }

    fun isDarkTheme(): Boolean {
        return prefs.getBoolean(KEY_IS_DARK_THEME, false) // По умолчанию возвращаем light theme
    }

    fun isDynamicTheme() : Boolean {
        return prefs.getBoolean(KEY_IS_DYNAMIC_THEME, false) // По умолчанию возвращаем light theme
    }

    fun isSystemTheme(): Boolean {
        return prefs.getBoolean(KEY_IS_SYSTEM_THEME, true) // По умолчанию возвращаем light theme
    }
}