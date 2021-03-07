package com.example.voiceko.Controller
import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.TestLooperManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getSystemService

import com.example.voiceko.CustAdapter.ExpandableListViewAdapter
import com.example.voiceko.DataBase.VoicekoDBContract
import com.example.voiceko.EnterState.EnterExpense
import com.example.voiceko.EnterState.EnterState
import com.example.voiceko.R
import com.example.voiceko.ui.EnterData
import com.example.voiceko.ui.FixCostActivity
import com.example.voiceko.ui.MainActivity
import java.time.LocalDateTime


class RecordController {
    private lateinit var dbmgr:VoicekoDBContract.DBMgr
    private lateinit var recordList:ArrayList<MutableMap<String,String>>
    private lateinit var activity: Context
    private var expandAmount = 0
    private  var incomeAmount = 0
    private lateinit var state: EnterState

    private constructor()

    public fun init(activity: Context){
        this.activity = activity
        dbmgr= VoicekoDBContract.DBMgr(this.activity)
        state = EnterExpense(this.activity)
        recordList = dbmgr.readRecord()


    }

    public fun reloadRecord(){
        expandAmount = 0
        incomeAmount = 0
        recordList = dbmgr.readRecord()
    }


    public fun loadRecordList(mYear:Int,mMonth:Int): ExpandableListViewAdapter {
        reloadRecord()
        val datePattern = Regex("^${mYear}/${mMonth}/")
        val dayPattern = Regex("(?<=${mYear}/${mMonth}/)[0-9]{0,}")
        val DataResult = arrayListOf<String>()
        val dayResult = arrayListOf<MutableMap<String,String>>()


        val Recordresult = arrayListOf<ArrayList<MutableMap<String,String>>>()
        var nowday = "0"
        for (record in recordList){
            if (datePattern.containsMatchIn(record["Date"]!!)){
                val day = dayPattern.find(record["Date"].toString())!!.value
                val info = mutableMapOf<String,String>()
                info["id"] = record["ID"]!!
                info["day"] = day
                info["cate"] = record["Category"]!!
                info["subCate"] = record["SubCategory"]!!
                info["amount"] = record["Amount"]!!
                info["type"] = record["Type"]!!

                if (day != nowday){
                    DataResult.add(record["Date"].toString())
                    nowday = day
                    if (dayResult.isNotEmpty()){
                        val temp = arrayListOf<MutableMap<String,String>>()
                        temp.addAll(dayResult)
                        Recordresult.add(temp)
                        dayResult.clear()
                    }
                }
                dayResult.add(info)
                calculateAmount(record["Amount"]!!.toInt(),record["Type"].toString())
            }
        }
        Recordresult.add(dayResult)
        return ExpandableListViewAdapter(activity, DataResult, Recordresult)
    }
    private fun calculateAmount(amount:Int,type:String){
        if (type == "支出"){
            expandAmount+=amount
        }else if(type == "收入" ){
            incomeAmount+=amount
        }
    }
    public fun addExpand(){
        expandAmount++
    }
    public fun getExpand():Int{
        return expandAmount
    }
    public fun getIncome():Int{
        return incomeAmount
    }

    public fun setRecordInfoToEnterData(activity: EnterData, id: String):Int{
        for(record in recordList){
            if (record.get("ID")!! == id){
                activity.setType(record["Type"]!!)
                activity.setDate(record["Date"]!!)
                activity.setAmount(record["Amount"]!!)
                activity.setCate(record["Category"]!!)
                activity.setSubCate(record["SubCategory"]!!)
                activity.setRemark(record["Remark"]!!)
            }
        }
        return id.toInt()
    }




    companion object {
        val instance = RecordController()
    }




}