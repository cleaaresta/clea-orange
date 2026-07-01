package com.example.clea_orange.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {

    // Gunakan ID baru agar sistem mereset pengaturan channel-nya
    private const val CHANNEL_ID = "channel_registrasi_terbaru"

    fun showNotification(
        context: Context,
        title: String,
        message: String,
        intent: Intent
    ) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Membuat Channel (Wajib untuk Android 8.0 ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notifikasi Akun",
                NotificationManager.IMPORTANCE_HIGH // Agar muncul sebagai pop-up di atas
            ).apply {
                description = "Channel untuk status pendaftaran"
            }
            manager.createNotificationChannel(channel)
        }

        val pending = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            // Menggunakan ikon launcher aplikasi agar pasti muncul
            .setSmallIcon(context.applicationInfo.icon) 
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pending)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Untuk Android di bawah 8.0
            .setDefaults(NotificationCompat.DEFAULT_ALL)  // Suara dan getaran default
            .build()

        // Kirim notifikasi
        manager.notify(1001, notification)
    }
}
