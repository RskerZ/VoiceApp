package com.example.voiceko.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.CircleProgressBar
import com.example.voiceko.Controller.BudgetController
import com.example.voiceko.Controller.RecordController
import com.example.voiceko.R
import kotlinx.android.synthetic.main.activity_set_budget.view.*
import java.util.*
import java.util.concurrent.ThreadPoolExecutor

class SetBudgetActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var setBtn: Button
    private lateinit var setEdit: EditText
    private lateinit var textViewBudgetBalance: TextView
    private lateinit var textViewBudgetValue: TextView
    private lateinit var textViewCostValue: TextView
    private lateinit var mBar:CircleProgressBar
    private var controller = BudgetController.instance
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var currentCost:Long = 0
    var budget:Long = 0
    var budgeBalance:Long = 0
    var percentage =0.0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_budget)
        this.init()
        this.loadInfo()
        setBtn.setOnClickListener{
            val limit = Long.MAX_VALUE.toString()
            val requestBudget = setEdit.text.toString()
            if (requestBudget.length > limit.length) {
                Toast.makeText(this, "抱歉，你所設定的預算值過高", Toast.LENGTH_SHORT).show()
            }else if (requestBudget.compareTo(limit) >0 && requestBudget.length == limit.length){
                Toast.makeText(this, "抱歉，你所設定的預算值過高", Toast.LENGTH_SHORT).show()
            }else if (requestBudget.isEmpty() || requestBudget.toLong() <= 0){
                Toast.makeText(this,"請輸入正常的預算",Toast.LENGTH_SHORT).show()
            }else{
                budget = requestBudget.toLong()
            }

            controller.updateBudget("TOTAL",budget)
            controller.loadDataFromBD()
            this.loadInfo()
            setEdit.setText("")
        }
        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "預算編制(${mYear}年${mMonth + 1}月)"
    }
    private fun init(){
        controller.init(this)
        this.loadNumber()
        toolbar = findViewById(R.id.budget_toolbar)
        //餘額條
        mBar = findViewById(R.id.circleProgressBar)
        setBtn = findViewById(R.id.budget_setBtn)
        setEdit = findViewById(R.id.budget_setEdit)
        //三個數字
        textViewBudgetBalance = findViewById(R.id.budget_balance)
        textViewBudgetValue = findViewById(R.id.budget_value)
        textViewCostValue = findViewById(R.id.budget_costValue)

        // 設定右上角的 menu
        toolbar.inflateMenu(R.menu.selectmonth);
        //更改預算
    }
    private fun loadNumber(){

        currentCost = controller.getCurrentCost()
        budget = controller.getBudget()
        budgeBalance = controller.getBudgetBalance()
        percentage = controller.getPercentage()
    }
    private fun loadInfo(){
        loadNumber()
        var color = Color.GREEN
        textViewBudgetBalance.setTextColor(Color.GREEN)
        //設定餘額顏色
        if(budgeBalance <= 0){
            textViewBudgetBalance.setTextColor(Color.RED)
            color = Color.RED
        }
        textViewBudgetValue.text = budget.toString()
        textViewCostValue.text = currentCost.toString()
        textViewBudgetBalance.text = budgeBalance.toString()
        if (budget > 0){
            mBar.setPercentage(percentage,color)
        }

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