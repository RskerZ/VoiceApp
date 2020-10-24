package com.example.voiceko.EnterState

import android.app.Activity
import com.example.voiceko.DataBase.DBMgr

abstract class EnterState() {
    lateinit var db:DBMgr
    constructor(activity: Activity) : this() {
        db = DBMgr(activity)
    }
    abstract fun saveRecord(date:String, amount :Int, cate : String, sub_cate:String, remark:String):Boolean
}