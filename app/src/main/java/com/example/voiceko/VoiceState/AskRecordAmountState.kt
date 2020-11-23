package com.example.voiceko.VoiceState

import com.example.voiceko.VoiceAPI.VoiceResultEnity
import java.util.*

class AskRecordAmountState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        val amount = msg.toIntOrNull()
        amount?.let {
            data!!.value.add(msg)
            controller.createMessage("確定要新增這筆紀錄嗎")
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)
            val type = data.value[0]
            val date = "${mYear}/${mMonth+1}/${mDay}"
            val subCate = data.value[2]
            val cate = data.value[1]
            val amount = data.value[3]
            val recordInfo =
                """
            ---------------
            $type
            日期:$date
            金額:$amount
            分類:$cate
            子分類:$subCate
            ---------------
            """.trimIndent()
            controller.createMessage(recordInfo)


            controller.setSatet(InsertNewRecordState())
        }?: kotlin.run {
            controller.createMessage("不好意思我不明白，請問總共花了多少錢呢?")
        }
        controller.startListen()
    }
}