package com.example.voiceko.VoiceState

import com.example.voiceko.VoiceAPI.VoiceResultEnity

class AskRecordTypeState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        if(msg != "支出" && msg != "收入"){
            controller.createMessage("不好意思我不明白，請問要新增在支出還是收入呢?")
        }else{
            var askMsg ="這次買了什麼呢?"
            if (msg == "收入"){
                askMsg = "你怎麼賺到的!"
            }
            data!!.value.add(msg)
            controller.createMessage(askMsg)
            controller.setSatet(AskRecordSubCate())
        }
    }
}