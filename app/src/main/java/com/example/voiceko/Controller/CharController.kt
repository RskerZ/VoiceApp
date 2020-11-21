package com.example.voiceko.Controller

import android.content.Context
import android.icu.text.SymbolTable
import com.example.voiceko.DataBase.VoicekoDBContract

class CharController private constructor() {
    private lateinit var context: Context
    private lateinit var dbMgr: VoicekoDBContract.DBMgr
    private  var eachCateAmount = ArrayList<MutableMap<String,String>>()
    private var month = ""
    private var year = ""
    companion object{
        val instance = CharController()
    }

    fun init(context: Context,year:String,month:String){
        this.context = context
        dbMgr = VoicekoDBContract.DBMgr(context)
        this.year = year
        this.month = month
    }
    fun setMonth(month: String){
        this.month = month
    }
    fun setYear(year: String){
        this.year = year
    }

    fun loadEachCateAmountFromDB(type:String){
        eachCateAmount = dbMgr.readEachCateAmount(type,year,month)
    }
    fun getEachCateAmount(): ArrayList<MutableMap<String,String>>{
        return eachCateAmount
    }
}