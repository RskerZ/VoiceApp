package com.example.voiceko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var CTR = Controller()
    var date = Calendar.getInstance()
    private lateinit var rePortBtn: Button
    private lateinit var AAABtn: Button
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
        AAABtn = findViewById<Button>(R.id.AAABtn)
        incomeText = findViewById<TextView>(R.id.incomeText)
        payText = findViewById<TextView>(R.id.payText)
        totalText = findViewById<TextView>(R.id.totalText)
        recordList = findViewById<ListView>(R.id.record)
        monthText = findViewById<TextView>(R.id.monthView)
        rePortBtn.setOnClickListener(testIncome)
        AAABtn.setOnClickListener(change)
        monthText.text = date.get(Calendar.YEAR).toString() + "年" + (date.get(Calendar.MONTH)+1).toString() + "月"
        totalText.text = (incomeText.text.toString().toInt() - payText.text.toString().toInt()).toString()
    }
    private var testIncome = View.OnClickListener {
        var adaper = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,testlist)
        recordList.adapter = adaper
    }
    private var change = View.OnClickListener {

    }
    /*var testIncome = View.OnClickListener {
        Toast.makeText(this,"fuck",Toast.LENGTH_LONG).show()
    }
    */


}
