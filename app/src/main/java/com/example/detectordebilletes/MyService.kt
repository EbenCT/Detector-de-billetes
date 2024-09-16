package com.example.detectordebilletes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private lateinit var volumeObserver: ContentObserver
    private var previousVolume: Int = 0

    override fun onCreate() {
        super.onCreate()
        Log.d("MiApp", "0 - Servicio creado") // Log 0
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        // Configuramos el ContentObserver para monitorear los cambios en el volumen
        volumeObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                if (currentVolume > previousVolume) {
                    Log.d("MiApp", "Subir volumen detectado") // Log para subir volumen
                } else if (currentVolume < previousVolume) {
                    Log.d("MiApp", "Bajar volumen detectado") // Log para bajar volumen
                }
                previousVolume = currentVolume
            }
        }

        // Registramos el observer
        contentResolver.registerContentObserver(
            Settings.System.CONTENT_URI, true, volumeObserver
        )
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MiApp", "1 - onStartCommand llamado") // Log 1
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
        Log.d("MiApp", "2 - Iniciando servicio en primer plano") // Log 2
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Servicio activo")
            .setContentText("Se está ejecutando correctamente")
            .build()
        startForeground(1, notification)
        Log.d("MiApp", "3 - Notificación mostrada") // Log 3
    }
}
