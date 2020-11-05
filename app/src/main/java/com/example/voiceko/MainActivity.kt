package com.example.voiceko
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.Controller.RecordController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var CTR = EnterDataController()
    private lateinit var incomeText: TextView
    private lateinit var payText: TextView
    private lateinit var totalText: TextView
    private lateinit var recordList: ListView
    private lateinit var toolbar: Toolbar
    private lateinit var controller:RecordController


    val c: Calendar = Calendar.getInstance()

    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    private lateinit var recordlist :ArrayList<ArrayList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        // 設定右上角的 menu
        toolbar.inflateMenu(R.menu.selectmonth)
        toolbar.setOnClickListener(editDate)
        toolbar.setOnMenuItemClickListener(selectMonthListener)

        val bottomNavigationView =
            findViewById(R.id.menuBtn) as BottomNavigationView
        bottomNavigationView.menu.setGroupCheckable(0, false, false)
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomBarListener)

        //載入記帳紀錄
        load_info()

    }


    var selectMonthListener= Toolbar.OnMenuItemClickListener{
        when(it.itemId){
            R.id.rightBtn -> {
                nextMonth()
                load_info()
                true
            }
            R.id.leftBtn -> {
                lastMonth()
                load_info()
                true
            }
            else ->{
                true
            }
        }
    }

    var bottomBarListener = BottomNavigationView.OnNavigationItemSelectedListener{item: MenuItem ->
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

    private var editDate = View.OnClickListener {

        var mDay=15

        DatePickerDialog(this, { _, mYear, mMonth, D ->
            run {
                this.mMonth = mMonth
                this.mYear = mYear
                mDay = D
                load_info()
            }
        },mYear , mMonth, mDay).show()
    }


    fun nextMonth(){
        if (mMonth == 11){
            mMonth = 0
            mYear++
        }else{
            mMonth++
        }
    }

    fun lastMonth(){
        if (mMonth == 0){
            mMonth = 11
            mYear--
        }else{
            mMonth--
        }
    }


    fun init(){
        controller = RecordController(this)
        incomeText = findViewById(R.id.incomeText)
        payText = findViewById(R.id.payText)
        totalText = findViewById(R.id.totalText)
        recordList = findViewById(R.id.record)
        toolbar = findViewById(R.id.main_toolbar)


    }

    fun load_info(){
        var listToShow = arrayListOf<String>()
        recordlist = controller.loadRecordList(mYear,mMonth)
        for (record in recordlist){
            listToShow.add(record[1])
        }
        toolbar.title = "${mYear}年${mMonth + 1}月"
        setRecord(listToShow)
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



}
