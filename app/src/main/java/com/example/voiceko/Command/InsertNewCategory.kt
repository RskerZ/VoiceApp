package com.example.voiceko.Command

import android.content.Context
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.Controller.VoiceController
import com.example.voiceko.VoiceAPI.VoiceResultEnity
import java.lang.Exception

class InsertNewCategory(context: Context):functionCommand(context) {
    override fun execute(data: VoiceResultEnity): Boolean {
        val controller = EnterDataController.instance
        val voiceController = VoiceController.instance
        try {
            val type = data.value[1]
            val cate = data.value[0]
            controller.init(context)
            var msg =""
            if(type == "收入"){
                controller.setStateToIncome()
            }

            val result =  controller.insertNewCate(cate)
            if (result == 200){
                msg = "已新增${cate}至${type}"
            }else if (result == 300){
                msg = "${type}已經存在:${cate}，勿重複新增"
            }else{
                msg = "分類新增失敗"
            }
            voiceController.createMessage(msg)
            return true
        }catch (e :Exception){
            return false
        }
    }
}