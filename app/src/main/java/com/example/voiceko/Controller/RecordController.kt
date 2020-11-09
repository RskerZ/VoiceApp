package com.example.voiceko.Controller

import android.app.Activity
import com.example.voiceko.DataBase.VoicekoDBContract
class RecordController() {
    private lateinit var dbmgr:VoicekoDBContract.DBMgr
    private lateinit var recordList:ArrayList<MutableMap<String,String>>
    private lateinit var activity: Activity
    constructor(activity: Activity) : this() {
        this.activity = activity
        dbmgr= VoicekoDBContract.DBMgr(this.activity)
        recordList = dbmgr.readRecord()
    }
    public fun reloadRecord(){
        recordList = dbmgr.readRecord()
    }
    public fun loadRecordList(mYear:Int,mMonth:Int): ArrayList<ArrayList<String>>{
        val datePattern = Regex("^${mYear}/${mMonth+1}")
        var result = arrayListOf<ArrayList<String>>()
        for (record in recordList){
            if (datePattern.containsMatchIn(record["Date"]!!)){
                val ID = record["ID"]
                val info = "${record["Date"]}| ${record["Category"]} | ${record["Amount"]}"
                result.add(arrayListOf(ID!!,info))
            }
        }
        return result
    }



}