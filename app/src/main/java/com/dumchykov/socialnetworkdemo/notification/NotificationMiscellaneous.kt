package com.dumchykov.socialnetworkdemo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.dumchykov.socialnetworkdemo.MainActivity
import com.dumchykov.socialnetworkdemo.R

private const val CHANNEL_ID = "Miscellaneous"
const val ON_ADD_CONTACT_NOTIFICATION_ID = 1000

fun createNotification(context: Context, contactId: Int): NotificationCompat.Builder {
    val deepLinkIntent = Intent(
        Intent.ACTION_VIEW,
        "https://www.example.com/detail/$contactId".toUri(),
        context,
        MainActivity::class.java
    )
    val deepLinkPendingIntent = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(deepLinkIntent)
        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Title")
        .setContentText("Content")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(deepLinkPendingIntent)
    return builder
}

fun createNotificationChannel(context: Context) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is not in the Support Library.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = /*context.getString(R.string.channel_name)*/"Channel name"
        val descriptionText = /*context.getString(R.string.channel_description)*/
            "Channel description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}