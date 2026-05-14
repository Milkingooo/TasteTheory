package com.example.coffeevibe.utils

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.memory.MemoryCache
import coil.disk.DiskCache

class CashApplication: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        val tm = ThemeManager(this)
        ThemeState.currentTheme = when {
            tm.isDarkTheme() -> ThemeMode.DARK
            tm.isDynamicTheme() -> ThemeMode.DYNAMIC
            tm.isSystemTheme() -> ThemeMode.SYSTEM
            else -> ThemeMode.LIGHT
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("images_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }
}