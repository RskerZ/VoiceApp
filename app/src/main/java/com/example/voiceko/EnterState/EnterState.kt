package com.example.voiceko.EnterState

import android.app.Activity
import com.example.voiceko.DataBase.VoicekoDBContract

abstract class EnterState() {
    lateinit var db: VoicekoDBContract.DBMgr
    constructor(activity: Activity) : this() {
        db = VoicekoDBContract.DBMgr(activity)
    }
    abstract fun saveRecord(date:String, amount :Int, cate : String, sub_cate:String, remark:String):Boolean
}