package com.example.detectordebilletes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MiApp", "3 - onStartCommand llamado") // Log 3
        createNotificationChannel()
        startServiceForeground()

        return START_STICKY
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("running_channel",
                "Mi Canal", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

        }
    }
    private fun startServiceForeground() {
        Log.d("MiApp", "6 - Iniciando servicio en primer plano") // Log 6
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Servicio activo")
            .setContentText("Se está ejecutando correctamente")
            .build()
        startForeground(1, notification)
        Log.d("MiApp", "7 - Notificación mostrada") // Log 7
    }
}
