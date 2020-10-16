package com.example.voiceko

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {
    var CTR = Controller()
    private lateinit var rePortBtn: Button
    private lateinit var enterDataBtn: Button
    private lateinit var incomeText: TextView
    private lateinit var payText: TextView
    private lateinit var totalText: TextView
    private lateinit var monthText: TextView
    private lateinit var recordList: ListView
    var testlist = arrayListOf<String>("A","B","C","B","C","B","C","B","C","B","C","B","C","B","C","B","C","B","C","B","C","B","C","B","C","B","C","B","C","B","C","B","C")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rePortBtn = findViewById<Button>(R.id.rePortBtn)
        enterDataBtn = findViewById<Button>(R.id.enterDataBtn)
        incomeText = findViewById<TextView>(R.id.incomeText)
        payText = findViewById<TextView>(R.id.payText)
        totalText = findViewById<TextView>(R.id.totalText)
        recordList = findViewById<ListView>(R.id.record)
        monthText = findViewById<TextView>(R.id.monthView)
        val Chartbtn = findViewById<Button>(R.id.ChartBtn)
        rePortBtn.setOnClickListener(testIncome)
        enterDataBtn.setOnClickListener(enter)
        Chartbtn.setOnClickListener(goChart)
        val c: Calendar = Calendar.getInstance()
        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        monthText.text = "${mYear}年${mMonth + 1}月"

        val bottomNavigationView =
            findViewById(R.id.menuBtn) as BottomNavigationView
        bottomNavigationView.menu.setGroupCheckable(0, false, false)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.mainMenu -> payText.setText("點選紀錄")
                R.id.addMenu -> payText.setText("點選新增")
                R.id.micMenu -> payText.setText("點選語音")
                R.id.reportMenu -> payText.setText("點選報表")
                R.id.setMenu -> payText.setText("點選設定")
            }
            true
        }
    }
    //測試的記帳紀錄
    private var testIncome = View.OnClickListener {
        //var adaper = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,testlist)
        //recordList.adapter = adaper
        createDialogWithoutDateField()!!.show()
    }

    private var enter = View.OnClickListener {
        var intent = Intent(this, EnterData::class.java)
        startActivity(intent)
    }
    //圖表區
    private  var goChart = View.OnClickListener {
        var intent = Intent(this, ChartActivity::class.java)
        startActivity(intent)
    }
    private fun createDialogWithoutDateField(): DatePickerDialog? {
        val dpd = DatePickerDialog(this, null, 2014, 1, 24)
        try {
            val datePickerDialogFields =
                dpd.javaClass.declaredFields
            for (datePickerDialogField in datePickerDialogFields) {
                if (datePickerDialogField.name == "mDatePicker") {
                    datePickerDialogField.isAccessible = true
                    val datePicker = datePickerDialogField[dpd] as DatePicker
                    val datePickerFields =
                        datePickerDialogField.type.declaredFields
                    for (datePickerField in datePickerFields) {
                        Log.i("test", datePickerField.name)
                        if ("mDaySpinner" == datePickerField.name) {
                            datePickerField.isAccessible = true
                            val dayPicker = datePickerField[datePicker]
                            (dayPicker as View).visibility = View.GONE
                        }
                    }
                }
            }
        } catch (ex: Exception) {
        }
        return dpd
    }



    /*var testIncome = View.OnClickListener {
        Toast.makeText(this,"fuck",Toast.LENGTH_LONG).show()
    }
    */


}
