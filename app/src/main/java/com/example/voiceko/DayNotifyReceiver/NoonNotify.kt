package com.example.voiceko.DayNotifyReceiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Toast
import com.example.voiceko.DayNotifactionChannel.DayNotify
import com.example.voiceko.ui.MainActivity
import kotlin.random.Random

class NoonNotify : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val dayNotify = DayNotify(context)
        val message = arrayListOf<String>("今天午餐吃什麼呢，要記得記帳呦", "中午吃飽飽了嗎，別忘記記帳呦", "你今天中午吃了什麼呢，打開手機記帳吧~")
        val rand = Random.nextInt(0,2)
        dayNotify.createNotify("吃完午餐了嗎?",message.get(rand))
    }

}
