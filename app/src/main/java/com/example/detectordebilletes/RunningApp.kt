package com.example.detectordebilletes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log

class RunningApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("MiApp", "1 - RunningApp onCreate iniciado") // Log 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("MiApp", "2 - Creando NotificationChannel") // Log 2
            val channel = NotificationChannel(
                "running_channel",
                "Running Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
