package com.example.voiceko.VoiceState

import com.example.voiceko.VoiceAPI.VoiceResultEnity

class BudgetState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        val amountPattern = Regex("""\d+""")
        val amount = amountPattern.find(msg)?.value
        amount?.let {
            data!!.value.add(msg)
            controller.executeCommand()
        }?: kotlin.run {
            controller.createMessage("請問預算要設定為多少呢?")
            controller.startListen()
        }
    }
}