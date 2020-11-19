package com.example.voiceko.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.Controller.PeriodRecordsController
import com.example.voiceko.R

class EditFixedCostActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var recordList: ListView
    private lateinit var controller : PeriodRecordsController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_fixed_cost)
        controller = PeriodRecordsController.instance
        controller.init(this)
        toolbar = findViewById(R.id.editFixedCost_toolbar)
        recordList = findViewById(R.id.editFixedCost_list)
        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // 設定右上角的 menu
        toolbar.inflateMenu(R.menu.editfixedcost_menu)

        var listdata = arrayListOf<String>("A","B","C","D","E","F","G","H")
        setRecord(listdata)
        //ItemClick
        recordList.setOnItemClickListener{parent, view, position, id ->
            val element = recordList.adapter.getItem(position) // The item that was clicked
            Toast.makeText(this, element.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    //返回鍵&設定右上按鈕作用
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.addFixedCost -> goAddFixedCost()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.editfixedcost_menu, menu)
        return true
    }
    private fun goAddFixedCost(){
        val intent = Intent(this, FixCostActivity::class.java)
        startActivity(intent)
    }
    private fun setRecord(recordData: ArrayList<String>){
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recordData)
        recordList.adapter = adapter
    }

}