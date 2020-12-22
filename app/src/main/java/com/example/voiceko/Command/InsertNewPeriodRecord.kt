package com.example.voiceko.Command

import android.content.Context
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.Controller.PeriodRecordsController
import com.example.voiceko.Record
import com.example.voiceko.VoiceAPI.VoiceResultEnity
import java.lang.Exception
import java.util.*

class InsertNewPeriodRecord(context: Context):functionCommand(context) {
    override fun execute(data: VoiceResultEnity): Boolean {
        try {
            val controller = PeriodRecordsController.instance
            controller.init(context)
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)
            val date = "${mYear}/${mMonth+1}/${mDay}"
            val subCate = data.value[0]
            val cate = data.value[1]
            val amount = data.value[2]
            val hour = data.value[3].toLong()
            controller.changeTypeToExpense()
            val record = Record(date, amount.toInt(),cate,subCate,"")
            controller.init(context)
            controller.createPeriodRecord(record,hour)
            return true
        }catch (e: Exception){
            return false
        }
    }
}