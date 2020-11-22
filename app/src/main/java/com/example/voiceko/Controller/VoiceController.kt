package com.example.voiceko.Controller

import com.beust.klaxon.Klaxon
import com.example.voiceko.Command.*
import com.example.voiceko.Message.UserMessage
import com.example.voiceko.VoiceAPI.VoiceResultEnity
import com.example.voiceko.VoiceState.*
import com.example.voiceko.ui.VoiceActivity

class VoiceController private constructor() {
    private lateinit var voiceActivity:VoiceActivity
    private var command:functionCommand? =null

    private var data:VoiceResultEnity?=null
    private var state:VoiceState = NormalState()
    var executeMsg = ""
    companion object{
        val instance = VoiceController()

    }
    fun setVoiceActivity(voiceActivity:VoiceActivity){
        this.voiceActivity = voiceActivity
    }


    fun sendMessage(msg: String){
        state.sendMessage(msg,data)
    }

    fun createMessage(msg:String){
        var message: UserMessage = UserMessage()
        message.createdAt = System.currentTimeMillis()
        message.message = msg
        message.nickname = "VoiceKo"
        message.id = 2
        voiceActivity.addMessage(message)
    }
    fun askAmountToSet(){
        createMessage("請問預算要設定為多少呢?")
    }



    fun startProcess(){
        voiceActivity.showDot()
    }
    fun doneProcess(){
        voiceActivity.hideDot()
    }
    fun formatResult(result:String){
        val temp = Klaxon().parse<VoiceResultEnity>(result)!!

        if(temp.Error.isNotEmpty()){
            createMessage("抱歉發生錯誤，請確認您的網路連線正常")
        }else if (temp.model == "0"){
            data = temp
            val canDo = judgeRequest(data!!.DetectResult)
            if (canDo){
                executeCommand()
            }
        }else{
            data!!.value.add(temp.DetectResult)
        }
    }
    fun judgeRequest(detectResult:String):Boolean{
        when(detectResult){
            "account" -> {
                command = InsertNewRecord(voiceActivity)
                createMessage("請問要新增在支出還是收入呢?")
                state = AskRecordTypeState()
                executeMsg = "已經成功新增囉"

            }
            "budget"->  {
                command = SetBudget(voiceActivity)
                state = BudgetState()
                if(data!!.value.isEmpty()){
                    askAmountToSet()
                    return false
                }
                executeMsg = "已經幫你設定好預算囉"
                return true

            }
            "category"-> {
                command = InsertNewCategory(voiceActivity)
                createMessage("請問要新增在支出還是收入呢?")
                state = AskCategoryTypeState()
            }
            "fixed"-> createMessage("你想要設定固定支出嗎?")
            "fixed_cate"-> createMessage("你想要設定某分類的固定支出嗎?")
            "income"-> {
                command = OpenChartActivity(voiceActivity)
                createMessage("將開啟報表頁面")
                return true
            }
        }
        return false
    }
    fun executeCommand(){
        val status = command?.execute(data!!)
        if (!status!!){
            executeMsg = "抱歉..我失敗了QQ"
        }
        if (executeMsg.length > 0){
            createMessage(executeMsg)
        }
        state = NormalState()
        executeMsg = ""
    }
    fun setSatet(state:VoiceState){
        this.state = state
    }

}