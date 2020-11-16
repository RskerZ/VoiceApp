package com.example.voiceko.Controller

import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.voiceko.PeriodRecords.PeriodReocrdsWorker
import com.example.voiceko.ui.MainActivity
import java.util.*
import java.util.concurrent.TimeUnit

class PeroidRecordsController private constructor() {
    companion object {
        val instance = PeroidRecordsController()
    }
//    var calendar = Calendar.getInstance()
//    var daysOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH)


}