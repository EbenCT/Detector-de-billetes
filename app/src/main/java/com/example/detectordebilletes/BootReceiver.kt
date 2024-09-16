package com.example.detectordebilletes
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            val serviceIntent = Intent(context, MyService::class.java)
            context?.startForegroundService(serviceIntent)
        } else if (intent?.action == "android.media.VOLUME_CHANGE") {
            Log.i("MiApp", "Presionaste el boton");
            // Mostrar notificación al cambiar el volumen (código existente)
            Toast.makeText(context, "Pulsaste un botón de volumen", Toast.LENGTH_SHORT).show();
        }
    }
}