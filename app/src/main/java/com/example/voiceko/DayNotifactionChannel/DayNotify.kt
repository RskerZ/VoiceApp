package com.example.voiceko.DayNotifactionChannel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import com.example.voiceko.R
import com.example.voiceko.ui.MainActivity

class DayNotify() {
    private lateinit var context:Context
    constructor(context: Context) : this() {
        this.context = context
    }

    private var channel : NotificationChannel = NotificationChannel(
        "DayNotify",
        "TimeToRecord",
        NotificationManager.IMPORTANCE_HIGH
    )

    fun createNotify(title: String, msg: String) {
        val builder = Notification.Builder(context, "DayNotify")
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        builder.setSmallIcon(R.drawable.voko)
            .setLargeIcon(
                BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.ic_launcher))
            .setContentTitle(title)
            .setContentText(msg)
            .setContentIntent(pendingIntent)
            .setCategory(Notification.CATEGORY_EVENT)
            .setShowWhen(true)
            .setAutoCancel(true)
        val notification : Notification = builder.build()
        val manager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(this.channel)
        manager.notify(1, notification)
    }//Notification
}