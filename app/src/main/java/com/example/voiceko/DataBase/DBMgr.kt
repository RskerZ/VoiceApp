package com.example.voiceko.DataBase

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import kotlin.coroutines.coroutineContext

class DBMgr( ) {
    private lateinit var consumptionRecordDbHelper : ConsumptionRecordContract.ConsumptionRecordDbHelper
    private lateinit var activity:Activity
    constructor(activity: Activity): this(){
        this.activity = activity
        openDB()
    }
    //openDB
    private fun openDB(){
        consumptionRecordDbHelper = ConsumptionRecordContract.ConsumptionRecordDbHelper(activity)
    }

    private fun closeDB(){
        consumptionRecordDbHelper.close()
    }

    public fun writeRecord(date:String, amount :Int, cate : String, sub_cate:String, remark:String, type:String):Boolean{
        try {
            var db = consumptionRecordDbHelper.writableDatabase
            var values = ContentValues()
            values.put("DATE",date)
            values.put("AMOUNT",amount)
            values.put("CATEGORY",cate)
            values.put("SUB_CATEGORY",sub_cate)
            values.put("REMARKS",remark)
            values.put("TYPE", type)
            db.insert("ConsumptionRecord",null,values)
            this.closeDB()
            return true

        }catch ( e: SQLiteException){
            this.closeDB()
            return false
        }

    }
}