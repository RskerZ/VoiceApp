package com.example.voiceko.Command

import android.content.Context
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.Record
import com.example.voiceko.VoiceAPI.VoiceResultEnity
import java.util.*

class InsertNewRecord(context: Context):functionCommand(context) {
    override fun execute(data: VoiceResultEnity): Boolean {
        val controller = EnterDataController.instance
        controller.init(context)
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        val type = data.value[0]
        val date = "${mYear}/${mMonth+1}/${mDay}"
        val subCate = data.value[2]
        val cate = data.value[1]
        val amount = data.value[3]
        if (type == "收入"){
            controller.setStateToIncome()
        }
        val record = Record(date, amount.toInt(),cate,subCate,"")
        controller.init(context)
        controller.saveRecord(record)
        return true
    }
}