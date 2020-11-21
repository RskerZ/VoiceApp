package com.example.voiceko.EnterState

import android.app.Activity
import android.content.Context
import com.example.voiceko.DataBase.VoicekoDBContract

abstract class EnterState() {
    lateinit var db: VoicekoDBContract.DBMgr
    lateinit var type:String
    constructor(activity: Context,type:String) : this() {
        db = VoicekoDBContract.DBMgr(activity)
        this.type = type
    }
    fun saveRecord(
        date: String,
        amount: Int,
        cate: String,
        sub_cate: String,
        remark: String
    ): Boolean {
        var result = db.writeRecord(date,amount,cate,sub_cate,remark,type)
        return result
    }

    fun updateRecord(
        recordID: Int,
        date: String,
        amount: Int,
        cate: String,
        sub_cate: String,
        remark: String
    ): Boolean {
        var result = db.updateRecord(recordID,date,amount,cate,sub_cate,remark,type)
        return result
    }
}