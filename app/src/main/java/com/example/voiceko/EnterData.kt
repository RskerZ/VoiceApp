package com.example.voiceko

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.w3c.dom.Text
import java.util.*

class EnterData : AppCompatActivity() {
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var mDay = c.get(Calendar.DAY_OF_MONTH)
    private val lilcaculater = LilCaculater()
    private lateinit var editTextDate: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var editTextNumber: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_data)
        editTextDate = findViewById<TextView>(R.id.editTextDate)
        toolbar = findViewById(R.id.title)
        editTextNumber = findViewById(R.id.editTextNumber)
        editTextNumber.setOnClickListener(editNumber)
        editTextDate.setOnClickListener(editDate)
        editTextDate.text = "${mYear}/${mMonth+1}/${mDay}"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
    //計算機
    private var editNumber = View.OnClickListener {
        editTextNumber.text = ""
        addFragment(lilcaculater)
    }

    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year/${month + 1}/$day"
    }

    private fun addFragment(f: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container,f)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
