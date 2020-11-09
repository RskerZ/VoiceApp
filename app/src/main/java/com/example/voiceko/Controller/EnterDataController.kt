package com.example.voiceko.Controller

import android.app.Activity
import com.example.voiceko.DataBase.VoicekoDBContract
import com.example.voiceko.EnterState.EnterExpense
import com.example.voiceko.EnterState.EnterIncome
import com.example.voiceko.EnterState.EnterState
import com.example.voiceko.ui.AccountItemType

class EnterDataController {
    private lateinit var state: EnterState
    private lateinit var activity:Activity
    private var type = "支出"
    lateinit var db: VoicekoDBContract.DBMgr
    private constructor()
    public fun init(activity: Activity){
        this.activity = activity
        state = EnterExpense(this.activity)
        db = VoicekoDBContract.DBMgr(activity)
    }

    public fun setStateToIncome(){
            this.state = EnterIncome(activity)
            type = "收入"
    }

    public fun setStateToExpense(){
            this.state = EnterExpense(activity)
            type = "支出"

    }

    public fun saveRecord(date:String, amount :Int, cate : String, subCate:String, remark:String):Boolean{
        return state.saveRecord(date,amount,cate,subCate,remark)
    }

    public fun loadCateList():ArrayList<String>{
        return db.readCateName(type)
    }

    public fun getType():String{
        return type
    }

    public fun insertNewCate(cate: String):Int{
        val status = db.insertNewCategory(cate,type)
        return status
    }

    companion object {
        val instance = EnterDataController()
    }
}