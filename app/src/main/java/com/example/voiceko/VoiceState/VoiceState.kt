package com.example.voiceko.VoiceState

import com.example.voiceko.Controller.VoiceController
import com.example.voiceko.VoiceAPI.VoiceResultEnity

abstract class VoiceState() {
    var controller = VoiceController.instance
    abstract fun sendMessage(msg:String, data: VoiceResultEnity?)

}