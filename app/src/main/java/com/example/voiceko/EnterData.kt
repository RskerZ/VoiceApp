package com.example.voiceko

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.util.*


class EnterData : AppCompatActivity() {
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var mDay = c.get(Calendar.DAY_OF_MONTH)
    val lilcaculater: Fragment = LilCaculater()
    val accItem: Fragment = AccountItemFragment()
    private lateinit var editTextDate: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var editTextNumber: TextView
    private lateinit var editTextType: TextView
    private lateinit var editTextSubType: TextView
    private lateinit var switchType: Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_data)
        editTextDate = findViewById<TextView>(R.id.editTextDate)
        toolbar = findViewById(R.id.title)
        editTextNumber = findViewById(R.id.editTextNumber)
        editTextType = findViewById(R.id.editType)
        editTextSubType = findViewById(R.id.editSubType)
        switchType = findViewById(R.id.switch1)

        editTextNumber.setOnClickListener(editNumber)
        editTextDate.setOnClickListener(editDate)
        editTextType.setOnClickListener(editType)
        //設定今天的日期
        editTextDate.text = "${mYear}/${mMonth+1}/${mDay}"
        //收入支出切換
        switchType.setOnCheckedChangeListener{ _, isCheck->
            if(isCheck) {//如果按開關，可以用此按鈕來改變是收入還是支出(若之後要編輯記帳紀錄可以用個TAG之類的東西紀錄他是支出還是收入，在Oncreate的時候就對switch按鍵進行變動)
                toolbar.setTitle("收入")
            }else{
                toolbar.setTitle("支出")
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

    //日期選擇器
    private var editDate = View.OnClickListener {

        DatePickerDialog(this, { _, mYear, mMonth, mDay ->
            run {
                val format = "${setDateFormat(mYear, mMonth, mDay)}"
                editTextDate.text = format
            }
        }, mYear, mMonth, mDay).show()
    }

    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year/${month + 1}/$day"
    }

    //計算機
    private var editNumber = View.OnClickListener {
        editTextNumber.text = ""
        showFragment("lil")

    }
    private var editType = View.OnClickListener {
        showFragment("acc")
    }



    //function
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
    }
}
