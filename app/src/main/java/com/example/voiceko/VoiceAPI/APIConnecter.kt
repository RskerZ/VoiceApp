package com.example.voiceko.VoiceAPI

import android.os.AsyncTask
import androidx.core.content.ContextCompat
import com.example.voiceko.Controller.VoiceController
import com.example.voiceko.Security.Security
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.logging.Logger

class APIConnecter: AsyncTask<String, Float, String>() {
    private lateinit var controller:VoiceController
    fun setController(controller:VoiceController){
        this.controller = controller
    }
    override fun onPreExecute() {
        controller.startProcess()
    }
    override fun doInBackground(vararg params: String?): String {
        var result ="NONE"
        if(params.isNotEmpty()){
            result = post(params[0],params[1])
        }
        return result

    }

    override fun onPostExecute(result: String?) {
        controller.formatResult(result!!)
        controller.doneProcess()
    }
    private fun post(text:String?,model:String?):String{
        var result=""

        try {
            if(text != null){
                val url_s = Security.SERVER_URL
                val url = URL(url_s)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type","application/json; utf-8")
                connection.setRequestProperty("Accept", "application/json")
                connection.doInput = true
                connection.doOutput = true
                connection.useCaches = false
                connection.readTimeout = 10000
                connection.connectTimeout = 10000

                val jsonObject = JSONObject()
                var textEncode = URLEncoder.encode(text,"UTF-8")
                var modelEncode = URLEncoder.encode(model,"UTF-8")
                jsonObject.put("text",textEncode)
                jsonObject.put("model",modelEncode)

                val outputStream = DataOutputStream(connection.outputStream)
                val post_string = jsonObject.toString()

                outputStream.writeBytes(post_string)
                outputStream.flush()
                outputStream.close()

                val inputStream = connection.inputStream
                val status = connection.responseCode
                if(status==200){
                    if(inputStream != null){
                        val reader = InputStreamReader(inputStream,"UTF-8")
                        val bfr = BufferedReader(reader)
                        var line = bfr.readLine()
                        while (line!= null){
                            result+=line+"\n"
                            line = bfr.readLine()
                        }
                    }else{
                        result = "{\"Error\":\"NO data\"}"
                    }
                }else{
                    result = "{\"Error\":\"API connection fail check your net\"}"
                }
                inputStream.close()
                connection.disconnect()
            }
        }catch (e:Exception){
            Logger.getLogger("API connect").warning(e.toString())
            result = "{\"Error\":\"API connection fail\"}"
        }

        return result

    }

}