package com.example.voiceko.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.CircleProgressBar
import com.example.voiceko.R
import java.util.*

class SetBudgetActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var setBtn: Button
    private lateinit var setEdit: EditText
    private lateinit var budgetBalance: TextView
    private lateinit var budgetValue: TextView
    private lateinit var costValue: TextView
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_budget)
        toolbar = findViewById(R.id.budget_toolbar)
        //餘額條
        val mBar = findViewById<CircleProgressBar>(R.id.circleProgressBar)

        setBtn = findViewById(R.id.budget_setBtn)
        setEdit = findViewById(R.id.budget_setEdit)
        //三個數字
        budgetBalance = findViewById(R.id.budget_balance)
        budgetValue = findViewById(R.id.budget_value)
        costValue = findViewById(R.id.budget_costValue)
        //把數值傳送到CirclePorgressBar
        mBar.setPercentage(budgetBalance.text.toString().toFloat() / budgetValue.text.toString().toFloat())

        // 設定右上角的 menu
        toolbar.inflateMenu(R.menu.selectmonth);

        //設定餘額顏色
        if(budgetBalance.text.toString().toInt() <= 0){
            budgetBalance.setTextColor(Color.RED)
        }else{
            budgetBalance.setTextColor(Color.GREEN)
        }

        //更改預算
        setBtn.setOnClickListener{
            budgetValue.text = setEdit.text
            mBar.setPercentage(budgetBalance.text.toString().toFloat() / budgetValue.text.toString().toFloat())
            setEdit.setText("")
        }


        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "預算編制(${mYear}年${mMonth + 1}月)"
    }
    //返回鍵
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()

        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.selectmonth, menu)
        return true
    }
}