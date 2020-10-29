package com.example.voiceko.EnterState

import android.app.Activity

class EnterExpense(activity: Activity) : EnterState(activity) {
    override fun saveRecord(
        date: String,
        amount: Int,
        cate: String,
        sub_cate: String,
        remark: String
    ): Boolean {
        var result = db.writeRecord(date,amount,cate,sub_cate,remark,"支出")
        return result
    }

}