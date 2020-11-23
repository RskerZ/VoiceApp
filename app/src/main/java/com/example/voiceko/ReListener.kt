package com.example.voiceko

import android.content.Context
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import com.example.voiceko.Controller.VoiceController

class ReListener(): RecognitionListener {
    val controller: VoiceController = VoiceController.instance
    override fun onReadyForSpeech(params: Bundle?) {
        Log.i("RecognitionListener", "onBeginningOfSpeech: ")
        controller.startProcess()
        Thread.sleep(500)
    }

    override fun onBeginningOfSpeech() {

    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.i("RMS", "onRmsChanged:{$rmsdB}")
    }

    override fun onBufferReceived(buffer: ByteArray?) {

    }

    override fun onEndOfSpeech() {
        controller.doneProcess()
    }

    override fun onError(error: Int) {
        var msg = ""
        when(error){
            2,1->{
                msg = "請確認你的網路連線正常"
            }
            7->{
                msg = "無法辨識"
            }
            8->{
                msg = "忙碌中，請稍後再試"
            }
            9->{
                msg = "請至設定開啟麥克風權限"
            }
        }
        controller.createMessage(msg)
    }

    override fun onResults(results: Bundle?) {
        val resList: ArrayList<String>? = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val sb = StringBuffer()
        if (resList != null) {
            sb.append(resList[0])
        }
        controller.sendMessage(sb.toString())
    }

    override fun onPartialResults(partialResults: Bundle?) {

    }

    override fun onEvent(eventType: Int, params: Bundle?) {

    }
}