package com.example.voiceko.EnterState

import android.app.Activity
import com.example.voiceko.DataBase.ConsumptionRecordContract

abstract class EnterState() {
    lateinit var db: ConsumptionRecordContract.DBMgr
    constructor(activity: Activity) : this() {
        db = ConsumptionRecordContract.DBMgr(activity)
    }
    abstract fun saveRecord(date:String, amount :Int, cate : String, sub_cate:String, remark:String):Boolean
}