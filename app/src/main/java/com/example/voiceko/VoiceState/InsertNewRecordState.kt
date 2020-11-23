package com.example.voiceko.VoiceState

import com.example.voiceko.VoiceAPI.VoiceResultEnity
import java.util.*
import kotlin.coroutines.coroutineContext

class InsertNewRecordState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {

        val sure = arrayListOf<String>("確定","好","是")
        val cancel = arrayListOf<String>("不要","不確定","不好","否","不是","取消")
        if (msg in sure){
            controller.executeCommand()
        }else if (msg in cancel){
            controller.createMessage("好的已取消")
            controller.setSatet(NormalState())
        }else{
            controller.createMessage("不好意思我不明白，請問確定要新增這筆紀錄嗎")
            controller.startListen()
        }
    }
}