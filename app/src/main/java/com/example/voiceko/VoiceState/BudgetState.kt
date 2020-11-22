package com.example.voiceko.VoiceState

import com.example.voiceko.VoiceAPI.VoiceResultEnity

class BudgetState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        val amount = msg.toIntOrNull()
        amount?.let {
            data!!.value.add(msg)
            controller.executeCommand()
        }?:controller.askAmountToSet()
    }
}