package com.example.voiceko

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class BackgroundService :Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }
}