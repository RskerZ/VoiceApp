package com.example.voiceko.VoiceState

import com.example.voiceko.VoiceAPI.VoiceResultEnity

class AskPeriodRecordAmountState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        val amount = msg.toIntOrNull()
        amount?.let {
            data!!.value.add(msg)
            if (data!!.value.size  == 3){
                controller.createMessage("請問要設定多久一次呢?")
                controller.setSatet(AskPeriodRecordCycleState())
            }else{
                controller.createMessage("請問是固定花費甚麼呢?")
                controller.setSatet(AskPeriodRecordSubCateState())
            }
        }?: kotlin.run {
            controller.createMessage("不好意思明白，請問要設定多少錢呢?")
        }
        controller.startListen()
    }
}