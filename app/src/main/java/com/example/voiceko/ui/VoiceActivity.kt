package com.example.voiceko.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voiceko.Message.MessageListAdapter
import com.example.voiceko.Message.UserMessage
import com.example.voiceko.R
import java.text.SimpleDateFormat
import java.util.*

class VoiceActivity : AppCompatActivity() {
    private lateinit var mMessageRecycler: RecyclerView
    private lateinit var mMessageAdapter: MessageListAdapter
    private lateinit var mEditText: EditText
    private lateinit var mSendBtn: Button
    private lateinit var toolbar: Toolbar

    private var messageList = mutableListOf<UserMessage>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)

        mEditText = findViewById(R.id.edittext_chatbox)
        mSendBtn = findViewById(R.id.button_chatbox_send)
        toolbar = findViewById(R.id.voice_toolbar)
        mSendBtn.setOnClickListener(sendText)

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
            makeMyMessage(mEditText.text.toString())
            mEditText.setText("")
        }else{
            Toast.makeText(this, "請輸入文字!!", Toast.LENGTH_SHORT).show()
        }


    }
    public fun makeMyMessage(text: String){
        var message: UserMessage = UserMessage()
        message.createdAt = System.currentTimeMillis()
        message.message = text
        message.nickname = "Me"
        message.id = 1
        messageList.add(message)
        mMessageAdapter = MessageListAdapter(this, messageList)
        mMessageRecycler.adapter = mMessageAdapter
    }
}