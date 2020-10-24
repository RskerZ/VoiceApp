package com.example.voiceko.Controller

import android.app.Activity
import com.example.voiceko.EnterState.EnterExpense
import com.example.voiceko.EnterState.EnterIncome
import com.example.voiceko.EnterState.EnterState

class EnterDataController() {
    private lateinit var state: EnterState
    private lateinit var activity:Activity

    constructor(activity: Activity) : this(){
        this.activity = activity
        state = EnterExpense(this.activity)
    }

    public fun setStateToIncome(){
        this.state = EnterIncome(activity)
    }

    public fun setStateToExpense(){
        this.state = EnterExpense(activity)
    }

    public fun saveRecord(date:String, amount :Int, cate : String, subCate:String, remark:String):Boolean{
        return state.saveRecord(date,amount,cate,subCate,remark)
    }
}