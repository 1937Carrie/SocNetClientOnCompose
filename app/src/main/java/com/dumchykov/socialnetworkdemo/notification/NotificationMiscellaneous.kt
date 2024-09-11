package com.dumchykov.socialnetworkdemo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.dumchykov.socialnetworkdemo.MainActivity
import com.dumchykov.socialnetworkdemo.R

private const val CHANNEL_ID = "Miscellaneous"
private const val ON_ADD_OR_DELETE_CONTACT_NOTIFICATION_ID = 1000

fun showNotification(
    context: Context,
    contactId: Int,
    name: String,
    takenAction: String,
) {
    val deepLinkIntent = Intent(
        Intent.ACTION_VIEW,
        "https://www.example.com/detail/$contactId".toUri(),
        context,
        MainActivity::class.java
    )

    val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(deepLinkIntent)
        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("#$contactId $name")
        .setContentText(takenAction)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(deepLinkPendingIntent)

    // Show the notification
    NotificationManagerCompat.from(context).notify(ON_ADD_OR_DELETE_CONTACT_NOTIFICATION_ID, builder.build())
}

private fun createNotificationChannel(context: Context) {
    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        val name = /*context.getString(R.string.channel_name)*/"Channel name"
        val descriptionText = /*context.getString(R.string.channel_description)*/
            "Channel description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}