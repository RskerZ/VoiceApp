package com.example.voiceko.VoiceState

import com.example.voiceko.VoiceAPI.APIConnecter
import com.example.voiceko.VoiceAPI.VoiceResultEnity

class AskPeriodRecordSubCateState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        data!!.value.add(msg)

        val apiConnecter = APIConnecter()
        apiConnecter.setController(controller)
        apiConnecter.execute(msg, "1")

        controller.createMessage("請問要設定多久一次呢?")
        controller.setSatet(AskPeriodRecordCycleState())
        controller.startListen()

    }
}