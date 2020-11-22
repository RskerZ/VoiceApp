package com.example.voiceko.EnterState

import android.app.Activity
import android.content.Context
import com.example.voiceko.DataBase.VoicekoDBContract
import com.example.voiceko.Record

abstract class EnterState() {
    lateinit var db: VoicekoDBContract.DBMgr
    lateinit var type:String
    constructor(activity: Context,type:String) : this() {
        db = VoicekoDBContract.DBMgr(activity)
        this.type = type
    }
    fun saveRecord(record:Record): Boolean {
        var result = db.writeRecord(record,type)
        return result
    }

    fun updateRecord(recordID: Int,record: Record): Boolean {
        var result = db.updateRecord(recordID,record,type)
        return result
    }
}