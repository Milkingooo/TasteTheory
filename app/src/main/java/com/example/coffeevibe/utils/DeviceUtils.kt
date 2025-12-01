package com.example.coffeevibe.utils

import android.content.Context
import android.content.pm.PackageManager

object DeviceUtils {
    fun getAppVersion(context: Context): String? {
        return try {
            val packageManager: PackageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "1.0.0"
        }
    }
}