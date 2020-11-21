package com.example.voiceko.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ExpandableListView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.Controller.PeriodRecordsController
import com.example.voiceko.CustAdapter.ExpandableListViewAdapter
import com.example.voiceko.R

class EditFixedCostActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var recordList: ExpandableListView
    private lateinit var controller : PeriodRecordsController

    private lateinit var recordListAdapter: ExpandableListViewAdapter

    var listdata = arrayListOf<String>("A","B","C","D","E","F","G","H")

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


        //載入Adapter
        //recordListAdapter = ???
        //setRecord(recordListAdapter)

        loadRecordList()


        //ItemClick
        recordList.setOnChildClickListener{ parent, v, groupPosition, childPosition, id ->
            val position = childPosition + groupPosition
            val workID = controller.getWorkId(position) // The item that was clicked
            val intent = Intent(this, FixCostActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, workID)
            }
            startActivity(intent)
            controller.setInsert(false)
            true
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

    override fun onRestart() {
        super.onRestart()
        loadRecordList()
    }
    private fun goAddFixedCost(){
        val intent = Intent(this, FixCostActivity::class.java)
        startActivity(intent)
        controller.setInsert(true)
    }

    private fun setRecord(adapter:ExpandableListViewAdapter){
        recordList.setAdapter(adapter)

    }
    private fun loadRecordList(){
        controller.readPeriodRecordFromDB()
        val adapter = controller.formatRecordToListView()
        setRecord(adapter)

    }

}