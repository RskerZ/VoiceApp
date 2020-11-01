package com.example.voiceko.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.voiceko.R
import java.util.*

class FixCostActivity : AppCompatActivity() {
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var mDay = c.get(Calendar.DAY_OF_MONTH)
    val lilcaculater: Fragment = LilCaculater("Fixedcost")
    val accItem: Fragment = AccountItemType("Fixedcost")
    val accSubItem: Fragment = SubItemType("Fixedcost")
    private lateinit var toolbar: Toolbar
    private lateinit var editTextDate: TextView
    private lateinit var editTextNumber: TextView
    private lateinit var editTextType: TextView
    private lateinit var editTextSubType: TextView
    private lateinit var switchType: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fix_cost)
        editTextDate = findViewById<TextView>(R.id.fixedcost_editTextDate)
        toolbar = findViewById(R.id.fixedcost_toolbar)
        editTextNumber = findViewById(R.id.fixedcost_editTextNumber)
        editTextType = findViewById(R.id.fixedcost_editType)
        editTextSubType = findViewById(R.id.fixedcost_editSubType)
        switchType = findViewById(R.id.fixedcost_switch)

        editTextNumber.setOnClickListener {
            editTextNumber.text = ""
            showFragment("lil")
        }
        editTextDate.setOnClickListener {
            DatePickerDialog(this, { _, mYear, mMonth, mDay ->
                run {
                    val format = "${setDateFormat(mYear, mMonth, mDay)}"
                    editTextDate.text = format
                }
            }, mYear, mMonth, mDay).show()
        }
        editTextType.setOnClickListener {
            showFragment("acc")
        }
        editTextSubType.setOnClickListener {
            showFragment("subacc")
        }


        //設定今天的日期
        editTextDate.text = "${mYear}/${mMonth+1}/${mDay}"

        //收入支出切換
        switchType.setOnCheckedChangeListener{ _, isCheck->
            if(isCheck) {//如果按開關，可以用此按鈕來改變是收入還是支出(若之後要編輯記帳紀錄可以用個TAG之類的東西紀錄他是支出還是收入，在Oncreate的時候就對switch按鍵進行變動)
                toolbar.setTitle("固定收入")
            }else{
                toolbar.setTitle("固定支出")
            }
        }

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val ft = supportFragmentManager.beginTransaction()
        hideFragment(ft)
        ft.commit()
    }

    //返回鍵
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    //function
    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year/${month + 1}/$day"
    }

    fun showFragment(f: String){
        val ft = supportFragmentManager.beginTransaction()
        hideFragment(ft)
        when(f){
            "lil" -> {
                if(lilcaculater.isAdded){
                    ft.show(lilcaculater)

                }else {
                    ft.add(R.id.fragment_container,lilcaculater)
                }
            }
            "acc" ->{
                if(accItem.isAdded){
                    ft.show(accItem)
                }else{
                    ft.add(R.id.fragment_container,accItem)
                }
            }
            "subacc" ->{
                if(accSubItem.isAdded){
                    ft.show(accSubItem)
                }else{
                    ft.add(R.id.fragment_container,accSubItem)
                }
            }
        }
        ft.addToBackStack(null)
        ft.commit()
    }

    fun hideFragment(ft: FragmentTransaction){
        if(lilcaculater.isAdded){
            ft.hide(lilcaculater)
        }
        if(accItem.isAdded){
            ft.hide(accItem)
        }
        if(accSubItem.isAdded){
            ft.hide(accSubItem)
        }
    }
}