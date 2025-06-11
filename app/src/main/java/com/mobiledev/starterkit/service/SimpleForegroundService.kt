package com.mobiledev.starterkit.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class SimpleForegroundService : Service() {

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "foreground_service_channel"
        const val ACTION_START = "START_SERVICE"
        const val ACTION_STOP = "STOP_SERVICE"
    }

    private var serviceJob: Job? = null
    private var counter = 0

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Log.d("ForegroundService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startForegroundService()
            ACTION_STOP -> stopForegroundService()
        }
        return START_STICKY // Restart if killed by system
    }

    private fun startForegroundService() {
        val notification = createNotification("Service started - Counter: $counter")

        // MUST call startForeground within 5 seconds of starting
        startForeground(NOTIFICATION_ID, notification)

        // Start background work
        serviceJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                delay(2000) // Wait 2 seconds
                counter++
                updateNotification("Running - Counter: $counter")
                Log.d("ForegroundService", "Counter: $counter")
            }
        }

        Log.d("ForegroundService", "Foreground service started")
    }

    private fun stopForegroundService() {
        serviceJob?.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        Log.d("ForegroundService", "Foreground service stopped")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW // Required: LOW or higher
            ).apply {
                description = "Channel for foreground service notifications"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(contentText: String): Notification {
        // Intent to stop service
        val stopIntent = Intent(this, SimpleForegroundService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Simple Foreground Service")
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.ic_media_play) // Use system icon
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true) // Cannot be dismissed by swipe
            .addAction(
                android.R.drawable.ic_media_pause,
                "Stop",
                stopPendingIntent
            )
            .build()
    }

    private fun updateNotification(contentText: String) {
        val notification = createNotification(contentText)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceJob?.cancel()
        Log.d("ForegroundService", "Service destroyed")
    }
}

