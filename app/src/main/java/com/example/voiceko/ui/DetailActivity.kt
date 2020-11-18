package com.example.voiceko.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.CustAdapter.EditTypeListAdapter
import com.example.voiceko.R
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var list: ListView
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        toolbar = findViewById(R.id.detail_toolbar)
        list = findViewById(R.id.detail_list)

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "詳細資訊(${mYear}年${mMonth + 1}月)"

        var listData = arrayListOf<String>("A","B","C","D","E")
        //載入類別資料到ListView
        setTypeList(listData)
    }
    //返回鍵
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()

        }
        return super.onOptionsItemSelected(item)
    }
    private fun setTypeList(ListData: ArrayList<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, ListData)
        list.adapter = adapter
    }
}