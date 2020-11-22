package com.example.voiceko.ui

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

    private var messageList = mutableListOf<UserMessage>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)
        controller = VoiceController.instance
        controller.setVoiceActivity(this)
        mEditText = findViewById(R.id.edittext_chatbox)
        mSendBtn = findViewById(R.id.button_chatbox_send)
        toolbar = findViewById(R.id.voice_toolbar)
        mSendBtn.setOnClickListener(sendText)
        dotLayout = findViewById<LinearLayout>(R.id.layout_dot)
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

    var sendText = View.OnClickListener {
        if(mEditText.text.toString() != "") {
            val msg = mEditText.text.toString()
            makeMyMessage(msg)
            mEditText.setText("")
            controller.sendMessage(msg)
        }
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
        enterMessageLayout.visibility = INVISIBLE
        dotLayout.visibility = VISIBLE
    }
    public fun hideDot(){
        enterMessageLayout.visibility = VISIBLE
        dotLayout.visibility = INVISIBLE
    }
}