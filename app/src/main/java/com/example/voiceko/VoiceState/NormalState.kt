package com.example.voiceko.VoiceState

import com.example.voiceko.Controller.VoiceController
import com.example.voiceko.VoiceAPI.APIConnecter
import com.example.voiceko.VoiceAPI.VoiceResultEnity

class NormalState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        val apiConnecter = APIConnecter()
        controller = VoiceController.instance
        apiConnecter.setController(controller)
        apiConnecter.execute(msg, "0")
    }
}