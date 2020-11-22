package com.example.voiceko.PeriodRecords

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.voiceko.Controller.PeriodRecordsController
import com.example.voiceko.R
import com.example.voiceko.Record
import com.example.voiceko.ui.EnterData
import com.example.voiceko.ui.MainActivity
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class PeriodReocrdsWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext,workerParams) {
    override fun doWork(): Result {
        val c: Calendar = Calendar.getInstance()
        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        var mDay = c.get(Calendar.DAY_OF_MONTH)
        var sMonth = (mMonth+1).toString()
        var sDay = mDay.toString()
        val date = "${mYear}/${sMonth}/${sDay}"
        val amount = inputData.getInt("amount",0)
        val cate = inputData.getString("cate")
        val subCate = inputData.getString("subcate")
        val remark = inputData.getString("remark")
        val type = inputData.getString("type")
        val record = Record(date,amount,cate!!,subCate!!, remark!!)
        val controller = PeriodRecordsController.instance
        controller.init(applicationContext)
        controller.saveRecordToDB(record ,type!!)
        // Indicate whether the work finished successfully with the Result
        return Result.success()

    }

}