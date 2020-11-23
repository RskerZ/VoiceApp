package com.example.voiceko.VoiceState

import com.example.voiceko.VoiceAPI.VoiceResultEnity

class AskCategoryTypeState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        if(msg != "支出" && msg != "收入"){
            controller.createMessage("不好意思我不明白，請問預算要要新增在支出還是收入呢?")
        }else{
            controller.createMessage("確定要在${msg}新增一項${data!!.value[0]}嗎")
            data.value.add(msg)
            controller.setSatet(InsertNewCategoryState())
        }
        controller.startListen()
    }
}