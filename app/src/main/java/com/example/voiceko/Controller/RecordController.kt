package com.example.voiceko.Controller

import android.app.Activity
import com.example.voiceko.CustAdapter.ExpandableListViewAdapter
import com.example.voiceko.DataBase.VoicekoDBContract
class RecordController() {
    private lateinit var dbmgr:VoicekoDBContract.DBMgr
    private lateinit var recordList:ArrayList<MutableMap<String,String>>
    private lateinit var activity: Activity
    private var expandAmount = 0
    private  var incomeAmount = 0
    constructor(activity: Activity) : this() {
        this.activity = activity
        dbmgr= VoicekoDBContract.DBMgr(this.activity)
        recordList = dbmgr.readRecord()
    }

    public fun reloadRecord(){
        expandAmount = 0
        incomeAmount = 0
        recordList = dbmgr.readRecord()
    }

    public fun loadRecordList(mYear:Int,mMonth:Int): ExpandableListViewAdapter {
        reloadRecord()
        val datePattern = Regex("^${mYear}/${mMonth+1}")
        val dayPattern = Regex("(?<=${mYear}/${mMonth+1}/)[0-9]{0,}")
        val DataResult = arrayListOf<String>()
        val dayResult = arrayListOf<String>()
        val Recordresult = arrayListOf<ArrayList<String>>()
        var nowday = "1"
        for (record in recordList){
            if (datePattern.containsMatchIn(record["Date"]!!)){
                val ID = record["ID"]
                val day = dayPattern.find(record["Date"].toString())!!.value
                val info = "${day}| ${record["Category"]}| ${record["SubCategory"]} | ${record["Amount"]}"
                if (day != nowday){
                    DataResult.add(record["Date"].toString())
                    nowday = day
                    if (dayResult.isNotEmpty()){
                        val temp = arrayListOf<String>()
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
    public fun getExpand():Int{
        return expandAmount
    }

    public fun getIncome():Int{
        return incomeAmount
    }



}