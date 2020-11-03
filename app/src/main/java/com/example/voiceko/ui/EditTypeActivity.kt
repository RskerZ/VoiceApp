package com.example.voiceko.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.R

class EditTypeActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var typeList: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_type)
        typeList = findViewById(R.id.typeList)
        //類別資料
        var typeListData = arrayListOf<String>("A","B","C","D","E")
        // 設定右上角的 menu
        toolbar = findViewById(R.id.edittype_toolbar)

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //Oncreate預設要是支出類別
        //載入類別資料到ListView
        setTypeList(typeListData)
    }
    //返回鍵
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> finish()
            R.id.costtype -> Toast.makeText(this, "支出", Toast.LENGTH_SHORT).show()
            R.id.incometype -> Toast.makeText(this, "收入", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
    //創造menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.typemenu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    //載入記帳紀錄資料
    private fun setTypeList(ListData: ArrayList<String>){
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ListData)
        typeList.adapter = adapter
    }
}