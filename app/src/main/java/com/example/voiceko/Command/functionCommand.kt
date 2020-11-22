package com.example.voiceko.Command

import android.content.Context
import com.example.voiceko.VoiceAPI.VoiceResultEnity

abstract class functionCommand(val context: Context) {

    abstract fun execute(data : VoiceResultEnity):Boolean

}