package com.example.voiceko.Controller

import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.beust.klaxon.Klaxon
import com.example.voiceko.Command.*
import com.example.voiceko.Message.UserMessage
import com.example.voiceko.ReListener
import com.example.voiceko.VoiceAPI.VoiceResultEnity
import com.example.voiceko.VoiceState.*
import com.example.voiceko.ui.VoiceActivity

class VoiceController private constructor() {
    private lateinit var voiceActivity:VoiceActivity
    private lateinit var reListener:ReListener
    private lateinit var speechRecognizer: SpeechRecognizer
    private var command:functionCommand? =null
    private var data:VoiceResultEnity?=null
    private var state:VoiceState = NormalState()
    var executeMsg = ""
    companion object{
        val instance = VoiceController()
    }
    fun setVoiceActivity(voiceActivity:VoiceActivity){
        this.voiceActivity = voiceActivity
        reListener = ReListener()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(voiceActivity)
        SpeechRecognizer.isRecognitionAvailable(voiceActivity)
        speechRecognizer.setRecognitionListener(reListener)
    }
    fun sendMessage(msg: String){
        voiceActivity.sentText(msg)
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
                if(data!!.value.isEmpty()){
                    state = BudgetState()
                    createMessage("請問預算要設定為多少呢?")
                }else{
                    executeMsg = "已經幫你設定好預算囉"
                    return true
                }
            }
            "category"-> {
                command = InsertNewCategory(voiceActivity)
                createMessage("請問要新增在支出還是收入呢?")
                state = AskCategoryTypeState()
            }
            "fixed"-> {
                command = InsertNewPeriodRecord(voiceActivity)
                if (data!!.value.isEmpty()){
                    createMessage("請問要設定多少錢呢?")
                    state = AskPeriodRecordAmountState()
                }else{
                    createMessage("請問是買甚麼東西的固定支出呢?")
                    state = AskPeriodRecordSubCateState()
                }
            }
            "fixed_cate"->{
                command = InsertNewPeriodRecord(voiceActivity)
                if (data!!.value.size == 2){
                    createMessage("請問要設定多少錢呢?")
                    state = AskPeriodRecordAmountState()
                }else{
                    createMessage("請問要設定多久一次呢?")
                    state = AskPeriodRecordCycleState()
                }
            }
            "income"-> {
                command = OpenChartActivity(voiceActivity)
                createMessage("將開啟報表頁面")
                return true
            }
        }
        startListen()
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
    fun startListen(){
        var intent= Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        speechRecognizer.startListening(intent)
    }




}