package com.example.voiceko.Command

import android.content.Context
import com.example.voiceko.Controller.BudgetController
import com.example.voiceko.VoiceAPI.VoiceResultEnity
import java.lang.Exception

class SetBudget(context: Context) : functionCommand(context) {
    override fun execute(data: VoiceResultEnity):Boolean {
        try {
            val controller = BudgetController.instance
            controller.init(this.context)

            controller.updateBudget("TOTAL",data.value[0].toLong())
            return true
        }catch (e:Exception){
            return false
        }

    }
}