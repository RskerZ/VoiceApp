package com.example.voiceko.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.R
import kotlinx.android.synthetic.main.activity_add_type.*

class AddTypeActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var switchType: Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_type)
        toolbar = findViewById(R.id.addtype_toolbar)
        switchType = findViewById(R.id.addtype_switch)
        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //收入支出切換
        switchType.setOnCheckedChangeListener{ _, isCheck->
            if(isCheck) {//如果按開關，可以用此按鈕來改變是收入還是支出(若之後要編輯記帳紀錄可以用個TAG之類的東西紀錄他是支出還是收入，在Oncreate的時候就對switch按鍵進行變動)
                switchType.text = "收入"
            }else{
                switchType.text = "支出"
            }
        }
    }
    //返回鍵
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}