package com.example.voiceko.Controller

import android.app.*
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.voiceko.DataBase.VoicekoDBContract
import com.example.voiceko.R
import com.example.voiceko.ui.MainActivity
import kotlin.math.roundToInt

class BudgetController private constructor() {
    private lateinit var activity: Activity
    private lateinit var db: VoicekoDBContract.DBMgr
    private  var recordController =RecordController.instance
    private var budget:Long = 0
    private var currentCost:Long = 0
    private var budgetBalance:Long = 0
    private var lessPercentage = 1.0f
    fun init(activity: Activity){
        this.activity = activity
        db = VoicekoDBContract.DBMgr(activity)
        recordController.init(activity)
        loadDataFromBD()
    }
    fun getBudget():Long{
        return budget
    }
    fun getCurrentCost():Long{
        return currentCost
    }
    fun getBudgetBalance():Long{
        return budgetBalance
    }
    fun getPercentage():Float{
        return lessPercentage
    }

    fun loadDataFromBD(){
        budget = db.getBudget("TOTAL")
        currentCost = recordController.getExpand().toLong()
        budgetBalance = budget - currentCost
        lessPercentage = (budgetBalance.toFloat() / budget.toFloat())
    }
    fun updateBudget(cateName:String,budget:Long){
        val result = db.updateBudget(cateName,budget)
        if (result){
            Toast.makeText(activity,"已經設定好預算囉",Toast.LENGTH_SHORT).show()
        }
    }
    fun checkBudget(){
        if (lessPercentage < 0.1){
            createNotifaction("這個月的支出已經很接近預算囉，該注意花費了呢。")
        }else if (lessPercentage <= 0.0){
            createNotifaction("哎呀呀，這個月支出超出預算啦，下個月要好好規劃囉。")
        }
    }
    fun createNotifaction(msg:String){
        val channel = NotificationChannel(
            "Budget",
            "BudgetNotification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val builder = Notification.Builder(activity, "Budget")
        val intent = Intent(activity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(activity, 0, intent, 0)
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("預算通知")
            .setContentText(msg)
            .setContentIntent(pendingIntent)
            .setCategory(Notification.CATEGORY_EVENT)
            .setShowWhen(true)
            .setAutoCancel(true)
        val notification : Notification = builder.build()
        val manager = activity.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        manager.notify(1, notification)
    }

    companion object{
        val instance = BudgetController()
    }
}