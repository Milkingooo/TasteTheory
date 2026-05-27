package com.example.coffeevibe.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit

object ViewModeState {
    var currentView: String by mutableStateOf("grid")
    var animationsEnabled: Boolean by mutableStateOf(true)
}

class AppearanceManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "AppearancePrefs"
        private const val KEY_VIEW_MODE = "viewMode"
        private const val KEY_ANIMATIONS = "animationsEnabled"
        private const val DEFAULT_VIEW = "grid"
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    init {
        ViewModeState.currentView = prefs.getString(KEY_VIEW_MODE, DEFAULT_VIEW) ?: DEFAULT_VIEW
        ViewModeState.animationsEnabled = prefs.getBoolean(KEY_ANIMATIONS, true)
    }

    fun getViewMode(): String {
        return prefs.getString(KEY_VIEW_MODE, DEFAULT_VIEW) ?: DEFAULT_VIEW
    }

    fun setViewMode(mode: String) {
        prefs.edit { putString(KEY_VIEW_MODE, mode) }
        ViewModeState.currentView = mode
    }

    fun areAnimationsEnabled(): Boolean {
        return prefs.getBoolean(KEY_ANIMATIONS, true)
    }

    fun setAnimationsEnabled(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_ANIMATIONS, enabled) }
        ViewModeState.animationsEnabled = enabled
    }
}
