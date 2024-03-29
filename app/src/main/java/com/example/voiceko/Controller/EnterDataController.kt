package com.example.voiceko.Controller

import android.app.Activity
import android.content.Context
import com.example.voiceko.DataBase.VoicekoDBContract
import com.example.voiceko.EnterState.EnterExpense
import com.example.voiceko.EnterState.EnterIncome
import com.example.voiceko.EnterState.EnterState
import com.example.voiceko.Record
import com.example.voiceko.ui.AccountItemType

class EnterDataController {
    private lateinit var state: EnterState
    private lateinit var activity:Context
    private lateinit var db: VoicekoDBContract.DBMgr
    private var cateID = arrayListOf<String>()
    private var cateList = arrayListOf<String>()
    private var cateWeight= arrayListOf<String>()
    private var type = "支出"
    private var budgetController= BudgetController.instance
    private var cate = ""
    private constructor()
    public fun init(activity: Context){
        this.activity = activity
        state = EnterExpense(this.activity)
        db = VoicekoDBContract.DBMgr(activity)
        budgetController.init(activity)
        setStateToExpense()
        cate = ""
    }

    public fun setStateToIncome(){
            this.state = EnterIncome(activity)
            type = "收入"
    }

    public fun setStateToExpense(){
            this.state = EnterExpense(activity)
            type = "支出"

    }

    public fun saveRecord(record:Record):Boolean{
        val result = state.saveRecord(record)
        budgetController.checkBudget()
        return result
    }

    public fun updateRecord(recordID:Int,record:Record):Boolean{
        val result = state.updateRecord(recordID, record)
        budgetController.checkBudget()
        return result
    }

    public fun deleteRecord(recordID: Int){
        db.deleteRecord(recordID)
    }

    public fun loadCateList():ArrayList<String>{
        val cateData = db.readCateName(type)
        cateID = cateData.get(0)
        cateList = cateData.get(1)
        cateWeight = cateData.get(2)
        return cateList
    }

    public fun loadCateWeight():ArrayList<Int>{
        val weightParseInt = arrayListOf<Int>()
        for (weight in cateWeight){
            weightParseInt.add(weight.toInt())

        }
        return weightParseInt
    }

    public fun getType():String{
        return type
    }

    public fun insertNewCate(cate: String):Int{
        val status = db.insertNewCategory(cate,type)
        return status
    }

    public fun changeCategoryName(newName:String,index:Int):Boolean{
        val id = cateID.get(index)
        val result = db.changeCateName(id, newName)
        return result
    }

    public fun changeCategoryWeight(newWeight:Int,index:Int):Boolean{
        val id = cateID.get(index)
        val result = db.changeCateWeight(id, newWeight)
        cateWeight[index] = newWeight.toString()
        return result
    }

    public fun deleteCategory(index:Int):Boolean{
        val id = cateID.get(index)
        val result = db.deleteCate(id)
        return result
    }

    public fun loadSubCateList():ArrayList<String>{
        var subCateList = arrayListOf<String>()
        if (cate.isNotEmpty()){
            subCateList = db.readSubCateName(cate)
        }

        return subCateList

    }

    public fun setCate(cate:String){
        this.cate = cate
    }


    companion object {
        val instance = EnterDataController()
    }
}