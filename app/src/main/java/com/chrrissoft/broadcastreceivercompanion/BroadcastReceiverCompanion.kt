package com.chrrissoft.broadcastreceivercompanion

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

abstract class BroadcastReceiverCompanion(
    private val title: String,
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context==null) return
        if (intent==null) return
        val notificationManager = NotificationManagerCompat.from(context).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                createNotificationChannel(createNotificationChannel())
            }
        }

        val subText = if (isOrderedBroadcast) "Ordered" else "Not ordered"
        val notification = NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentTitle(title)
            .setSubText(subText)
            .build()
        notificationManager.notify(nextInt(), notification)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun createNotificationChannel(): NotificationChannelCompat {
        return NotificationChannelCompat
            .Builder(NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH)
            .setName(NOTIFICATION_CHANNEL_NAME)
            .build()
    }

    class ManifestBroadcastReceiverCompanion
        : BroadcastReceiverCompanion("Manifest Companion")

    class ContextBroadcastReceiverCompanion @Inject constructor()
        : BroadcastReceiverCompanion("Context Companion")

    companion object {
        private const val NOTIFICATION_CHANNEL_ID =
            "com.chrrissoft.broadcastreceivercompanion.broadcasts"

        private const val NOTIFICATION_CHANNEL_NAME = "Broadcasts"

        private const val CONTEXT_BROADCAST =
            "com.chrrissoft.broadcastreceiver.CUSTOM_CONTEXT_BROADCASTS_COMPANION"
        val filter = IntentFilter(CONTEXT_BROADCAST)
    }
}
