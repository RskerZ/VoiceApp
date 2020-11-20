package com.example.voiceko.Controller

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.Toast
import androidx.work.*
import com.example.voiceko.DataBase.VoicekoDBContract
import com.example.voiceko.PeriodRecords.PeriodReocrdsWorker
import com.example.voiceko.R
import com.example.voiceko.ui.FixCostActivity
import com.example.voiceko.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class PeriodRecordsController {
    private lateinit var activity:Activity
    private lateinit var dbMgr: VoicekoDBContract.DBMgr
    private var type = "支出"
    private var enterDataController = EnterDataController.instance
    private var isInsert = true
    private var periodRecordList = arrayListOf<MutableMap<String,String>>()
    private var workIDList = arrayListOf<String>()
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

    fun createWorkRequest(input: Data, hours: Long, waitTime:Long, replace:Boolean, id:String? = null):String{
        val tsLong = System.currentTimeMillis() / 1000
        var ts = tsLong.toString()
        var policy = ExistingPeriodicWorkPolicy.KEEP
        if (replace){
            policy = ExistingPeriodicWorkPolicy.REPLACE
            ts = id!!
        }

        val worker = PeriodicWorkRequestBuilder<PeriodReocrdsWorker>(hours, TimeUnit.MINUTES)
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
        var workID = createWorkRequest(inputData, hours, timeToWait, false)
        dbMgr.insertNewPeriodRecord(workID, hours,date, amount, cate, subCate, remark, type)
    }

    fun modifyPeriodRecord(
        date: String,
        amount: Int,
        cate: String,
        subCate: String,
        remark: String,
        hours: Long,
        workID : String
    ){
        val timeToWait = calculateMinutes(date)
        val inputData = createRecordInputData(amount, cate, subCate, remark, type)
        createWorkRequest(inputData, hours, timeToWait, true, workID)
        dbMgr.updatePeriodRecord( workID, hours,date, amount, cate, subCate, remark, type)
    }

    fun cancelPeriodWork(workID: String){
        val workManager = WorkManager.getInstance(activity)
        workManager.cancelUniqueWork(workID)
        dbMgr.deletePeriodRecord(workID)
    }

    fun savePeriodWork(date: String,
                       amount: Int,
                       cate: String,
                       subCate: String,
                       remark: String,
                       hours: Long,
                       workID : String? = null){
        if (isInsert){
            createPeriodRecord(date,amount,cate, subCate, remark, hours)
        }else{
            modifyPeriodRecord(date,amount,cate, subCate, remark, hours,workID!!)
        }
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
        val builder = Notification.Builder(activity, "PeriodRecord")
        val intent = Intent(activity,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(activity, 0, intent, 0)
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("固定收支")
            .setContentText("已新增一筆${cate} \$${amount}的紀錄")
            .setContentIntent(pendingIntent)
            .setCategory(Notification.CATEGORY_EVENT)
            .setShowWhen(true)
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

    fun setInsert(b:Boolean){
        this.isInsert = b
    }

    fun readPeriodRecordFromDB(){
        periodRecordList = dbMgr.readPeriodRecord()
    }
    fun formatRecordToListView():ArrayList<String>{
        var recordList = arrayListOf<String>()
        workIDList.clear()
        for (record in periodRecordList){
            val workID = record["workID"]
            val date = record["date"]
            val amount = record["amount"]
            val cate = record["cate"]
            val result = "開始日期${date}|${cate}|${amount}"
            recordList.add(result)
            workIDList.add(workID!!)
        }
        return recordList
    }
    fun getWorkId(index:Int):String{
        return workIDList.get(index)
    }

    fun setRecordInfoToFixCostActivity(activity: FixCostActivity,mworkID: String){
        for (record in periodRecordList){
            val workID = record["workID"]
            if (workID == mworkID){
                val date = record["date"]
                val amount = record["amount"]
                val cate = record["cate"]
                val subCate = record["subCate"]
                val cycleTime = record["cycle"]
                val remark = record["remark"]
                val type = record["type"]
                activity.setDate(date!!)
                activity.setAmount(amount!!)
                activity.setCate(cate!!)
                activity.setSubCate(subCate!!)
                activity.setCycle(cycleTime!!)
                activity.setRemark(remark!!)
                activity.setSwitch(type!!)

            }

        }

    }

}