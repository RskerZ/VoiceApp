package com.example.voiceko.VoiceState

import com.example.voiceko.VoiceAPI.VoiceResultEnity
import java.util.*

class AskPeriodRecordCycleState:VoiceState() {
    override fun sendMessage(msg: String, data: VoiceResultEnity?) {
        val cycle = msg
        val day = arrayListOf<String>("每天","一天","天","天天","每日","每一天","每一日","一日")
        val week = arrayListOf<String>("每周","每週","每星期","一週","一周","一星期","周","週","星期","每一週","每一周","每一星期")
        val month = arrayListOf<String>("每月","一個月","月","每個月","每一月")
        val year = arrayListOf<String>("每年","一年","年","年年","每一載","每載","一載")

        var hour = 0
        when(cycle){
            in day-> hour = 24
            in week -> hour = 168
            in month -> hour = 732
            in year -> hour = 8766
        }
        if (hour == 0){
            controller.createMessage("不好意思我不清楚你的意思，請問要:每天、每周、每月還是每年一次呢?")
        }else{
            data!!.value.add(hour.toString())
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)
            val type = "支出"
            val date = "${mYear}/${mMonth+1}/${mDay}"
            val subCate = data.value[0]
            val cate = data.value[1]
            val amount = data.value[2]
            val hours = data.value[3].toLong()
            val cycleTimeHours = arrayListOf<Long>(24, 168, 732, 8766)
            val cycleName = arrayListOf<String>("每天","每周","每月","每年")
            val recordInfo =
                """
            ---------------
            $type
            日期:$date
            金額:$amount
            分類:$cate
            子分類:$subCate
            ${cycleName[cycleTimeHours.indexOf(hours)]}記錄一次
            ---------------
            """.trimIndent()
            controller.createMessage("確定要新增這筆固定支出嗎?")
            controller.createMessage(recordInfo)
            controller.setSatet(InsertNewPeriodRecordState())
        }
        controller.startListen()

    }
}