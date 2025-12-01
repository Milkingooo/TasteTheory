package com.example.coffeevibe.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import com.example.coffeevibe.R

object NotificationUtils {
    private const val CHANNEL_ID = "default_channel"
    private const val NOTIFICATION_ID = 1000

    @SuppressLint("ObsoleteSdkInt")
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Default Channel"
            val description = "This is the default channel for notifications."
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("ObsoleteSdkInt", "NotificationPermission")
    fun sendSimpleNotification(context: Context, title: String, message: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder: Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = Notification.Builder(context, CHANNEL_ID)
        } else {
            builder = Notification.Builder(context)
        }

        builder.setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(Icons.Filled.Notifications.toString().toInt()) // Убедитесь, что у вас есть иконка для уведомлений

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}