package com.example.voiceko
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.DataBase.ConsumptionRecordContract
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {
    var CTR = EnterDataController()
    private lateinit var incomeText: TextView
    private lateinit var payText: TextView
    private lateinit var totalText: TextView
    private lateinit var recordList: ListView
    private lateinit var toolbar: Toolbar
    val c: Calendar = Calendar.getInstance()

    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    private lateinit var testlist :ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var dbmgr = ConsumptionRecordContract.DBMgr(this)
        testlist = dbmgr.readRecord()
        incomeText = findViewById(R.id.incomeText)
        payText = findViewById(R.id.payText)
        totalText = findViewById(R.id.totalText)
        recordList = findViewById(R.id.record)

        toolbar = findViewById(R.id.main_toolbar)
        // 設定右上角的 menu
        toolbar.inflateMenu(R.menu.selectmonth)
        toolbar.title = "${mYear}年${mMonth + 1}月"

        val bottomNavigationView =
            findViewById(R.id.menuBtn) as BottomNavigationView
        bottomNavigationView.menu.setGroupCheckable(0, false, false)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.addMenu -> goEnter()
                R.id.micMenu -> {
                    Toast.makeText(this, "你好我會語音辨識", Toast.LENGTH_SHORT).show()
                }
                R.id.reportMenu -> goChart()
                R.id.setMenu -> goSetting()
            }
            true
        }
        //載入記帳紀錄
        setRecord(testlist)
    }

    //切換Activity
    fun goChart(){
        var intent = Intent(this, ChartActivity::class.java)
        startActivity(intent)
    }
    fun goEnter(){
        var intent = Intent(this, EnterData::class.java)
        startActivity(intent)
    }
    fun goSetting(){
        var intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }
    fun makeToast(msg:String){
        Toast.makeText(this,msg.toString(),Toast.LENGTH_SHORT).show()
    }
    //載入記帳紀錄資料
    private fun setRecord(recordData: ArrayList<String>){
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recordData)
        recordList.adapter = adapter
    }






    /*var testIncome = View.OnClickListener {
        Toast.makeText(this,"fuck",Toast.LENGTH_LONG).show()
    }
    */


}
