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

class NightNotify : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val dayNotify = DayNotify(context)
        val message = arrayListOf<String>("今天快過完了呢，有記得記帳嗎", "今天辛苦囉，今天的花費都有好好記錄嗎", "晚餐吃飽了，那就來記帳嗎")
        val rand = Random.nextInt(0,2)
        dayNotify.createNotify("已經天黑了呢~",message.get(rand))
    }

}
