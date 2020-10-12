package com.example.voiceko

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
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
        rePortBtn.setOnClickListener(testIncome)
        enterDataBtn.setOnClickListener(enter)
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

    private var testIncome = View.OnClickListener {
        var adaper = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,testlist)
        recordList.adapter = adaper
    }

    private var enter = View.OnClickListener {
        var intent = Intent(this, EnterData::class.java)
        startActivity(intent)
    }

    /*var testIncome = View.OnClickListener {
        Toast.makeText(this,"fuck",Toast.LENGTH_LONG).show()
    }
    */


}
