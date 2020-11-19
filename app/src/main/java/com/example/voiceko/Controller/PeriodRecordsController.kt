package com.example.voiceko.Controller

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.work.*
import com.example.voiceko.DataBase.VoicekoDBContract
import com.example.voiceko.PeriodRecords.PeriodReocrdsWorker
import com.example.voiceko.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class PeriodRecordsController {
    private lateinit var activity:Activity
    private lateinit var dbMgr: VoicekoDBContract.DBMgr
    private var type = "支出"
    private var enterDataController = EnterDataController.instance
    private constructor()
   fun init(activity: Activity){
        this.activity = activity
        enterDataController.init(activity)
        dbMgr= VoicekoDBContract.DBMgr(this.activity)
    }
    companion object{
        val instance = PeriodRecordsController()
    }

    private fun createRecordInputData(
        amount: Int,
        cate: String,
        subCate: String,
        remark: String,
        type: String
    ): Data {
        val builder = Data.Builder()
        builder.putInt("amount", amount)
        builder.putString("cate", cate)
        builder.putString("subcate", subCate)
        builder.putString("remark", remark)
        builder.putString("type", type)
        return builder.build()
    }

    fun createWorkRequest(input: Data, hours: Long, waitTime:Long):String{
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        var policy = ExistingPeriodicWorkPolicy.KEEP

        val worker = PeriodicWorkRequestBuilder<PeriodReocrdsWorker>(hours, TimeUnit.HOURS)
            .setInitialDelay(waitTime,TimeUnit.MINUTES)
            .addTag(ts)
            .setInputData(input)
            .build()

        val workManager = WorkManager.getInstance(activity)
        workManager.enqueueUniquePeriodicWork(
            ts,
            policy,
            worker
        )
        Toast.makeText(activity,"已設置好，將在${waitTime}分鐘後開始",Toast.LENGTH_SHORT).show()

        return ts

    }

    fun changeTypeToIncome(){
        enterDataController.setStateToIncome()
        type = "收入"
    }
    fun changeTypeToExpense(){
        enterDataController.setStateToExpense()
        type = "支出"
    }

    fun createPeriodRecord(
        date: String,
        amount: Int,
        cate: String,
        subCate: String,
        remark: String,
        hours: Long
    ){
        val timeToWait = calculateMinutes(date)
        var inputData = createRecordInputData(amount, cate, subCate, remark, type)
        var workID = createWorkRequest(inputData, hours, timeToWait)
        dbMgr.insertNewPeriodRecord(workID, hours,date, amount, cate, subCate, remark, type)
    }
    fun calculateMinutes(date: String):Long{
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN)
        val toDate =  formatter.parse(date)
        val millionSeconds = toDate!!.time - Calendar.getInstance().timeInMillis
        val timeToWait = TimeUnit.MILLISECONDS.toMinutes(millionSeconds).toLong()
        if (timeToWait>0){
            return timeToWait
        }else{
            return 0
        }
    }

    fun createNotificationChannel(cate: String, amount: Int) {
        val channel = NotificationChannel(
            "PeriodRecord",
            "PeriodRecordNotification",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.lightColor = Color.WHITE
        val builder = Notification.Builder(activity, "PeriodRecord")
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("固定收支")
            .setContentText("已新增一筆${cate} \$${amount}的紀錄")
            .setAutoCancel(true)
        val notification : Notification = builder.build()
        val manager = activity.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        manager.notify(1, notification)
    }//Notification

    fun saveRecordToDB(
        date: String,
        amount: Int,
        cate: String,
        subCate: String,
        remark: String,
        type: String
    ){
        if (type == "支出"){
            enterDataController.setStateToExpense()
        }else if(type == "收入"){
            enterDataController.setStateToIncome()
        }
        val result = enterDataController.saveRecord(date, amount, cate, subCate, remark)
        if (result){
            createNotificationChannel(cate, amount)
        }

    }
}