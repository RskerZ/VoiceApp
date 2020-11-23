package com.example.voiceko.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voiceko.Controller.VoiceController
import com.example.voiceko.Message.MessageListAdapter
import com.example.voiceko.Message.UserMessage
import com.example.voiceko.R
import com.example.voiceko.ReListener
import com.example.voiceko.VoiceState.NormalState
import kotlinx.android.synthetic.main.activity_voice.*
import java.text.SimpleDateFormat
import java.util.*

class VoiceActivity : AppCompatActivity() {
    private lateinit var mMessageRecycler: RecyclerView
    private lateinit var dotLayout:LinearLayout
    private lateinit var enterMessageLayout: LinearLayout
    private lateinit var mMessageAdapter: MessageListAdapter
    private lateinit var controller:VoiceController
    private lateinit var mEditText: EditText
    private lateinit var mSendBtn: Button
    private lateinit var toolbar: Toolbar
    private lateinit var micBtn:Button
    private lateinit var micLayout: LinearLayout


    private var messageList = mutableListOf<UserMessage>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)
        controller = VoiceController.instance
        controller.setVoiceActivity(this)
        mEditText = findViewById(R.id.edittext_chatbox)
        mSendBtn = findViewById(R.id.button_chatbox_send)
        toolbar = findViewById(R.id.voice_toolbar)
        mSendBtn.setOnClickListener(sendListener)
        dotLayout = findViewById<LinearLayout>(R.id.layout_dot)
        micBtn=findViewById(R.id.mic_button)
        micLayout = findViewById(R.id.layout_mic)
        micBtn.setOnClickListener(startListener)

        enterMessageLayout = findViewById(R.id.layout_chatbox)
        mMessageRecycler = findViewById(R.id.reyclerview_message_list)
        mMessageAdapter = MessageListAdapter(this, messageList)
        mMessageRecycler.layoutManager = LinearLayoutManager(this)
        mMessageRecycler.adapter = mMessageAdapter

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
    //返回鍵
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    var sendListener = View.OnClickListener {
        if(mEditText.text.toString() != "") {
            val msg = mEditText.text.toString()
            sentText(msg)
        }
    }

    var startListener = View.OnClickListener{
        controller.startListen()

    }

    public fun sentText(msg:String){
        makeMyMessage(msg)
        mEditText.setText("")
    }


    public fun makeMyMessage(text: String){
        var message: UserMessage = UserMessage()
        message.createdAt = System.currentTimeMillis()
        message.message = text
        message.nickname = "Me"
        message.id = 1
        addMessage(message)

    }
    public fun addMessage(message:UserMessage){
        messageList.add(message)
        mMessageAdapter = MessageListAdapter(this, messageList)
        mMessageRecycler.adapter = mMessageAdapter
    }
    public fun showDot(){
        micLayout.visibility = INVISIBLE
        dotLayout.visibility = VISIBLE
    }
    public fun hideDot(){
        micLayout.visibility = VISIBLE
        dotLayout.visibility = INVISIBLE
    }

    override fun onDestroy() {

        controller.setSatet(NormalState())
        super.onDestroy()
    }

}