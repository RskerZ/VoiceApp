package com.example.voiceko.VoiceState
import com.example.voiceko.VoiceAPI.APIConnecter
import com.example.voiceko.VoiceAPI.VoiceResultEnity

class AskRecordSubCate:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        if (msg.isNotEmpty()){
            data!!.value.add(msg)
            val apiConnecter = APIConnecter()
            apiConnecter.setController(controller)
            apiConnecter.execute(msg, "1")

            val type =data.value[0]
            var askMsg = "總共花了多少錢呢?"
            if (type == "收入"){
                askMsg = "總共賺了多少錢呢?"
            }


            controller.createMessage(askMsg)
            controller.setSatet(AskRecordAmountState())
            controller.startListen()
        }


    }
}