package com.example.voiceko.Command

import android.content.Context
import android.content.Intent
import com.example.voiceko.VoiceAPI.VoiceResultEnity
import com.example.voiceko.ui.ChartActivity

class OpenChartActivity(context: Context):functionCommand(context) {
    override fun execute(data: VoiceResultEnity): Boolean {
        val intent = Intent(context, ChartActivity::class.java)
        context.startActivity(intent)
        return true
    }
}