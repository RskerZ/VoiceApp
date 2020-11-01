package com.example.voiceko.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.R

class SettingActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var gofixedbtn: Button
    private lateinit var goaddtypebtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar = findViewById(R.id.setting_toolbar)

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        gofixedbtn = findViewById(R.id.setting_fixedbtn)
        goaddtypebtn = findViewById(R.id.setting_addtypebtn)
        gofixedbtn.setOnClickListener {
            var intent = Intent(this, FixCostActivity::class.java)
            startActivity(intent)
        }
        goaddtypebtn.setOnClickListener {
            var intent = Intent(this, AddTypeActivity::class.java)
            startActivity(intent)
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