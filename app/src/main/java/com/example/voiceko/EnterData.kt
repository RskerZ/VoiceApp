package com.example.voiceko

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.voiceko.Controller.EnterDataController
import java.util.*


class EnterData : AppCompatActivity() {
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var mDay = c.get(Calendar.DAY_OF_MONTH)
    val lilcaculater: Fragment = LilCaculater("Enter")
    val accItem: Fragment = AccountItemType("Enter")


    private lateinit var editTextDate: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var editTextNumber: TextView
    private lateinit var editTextType: TextView
    private lateinit var editTextSubType: TextView
    private lateinit var remarkEditBox:TextView
    private lateinit var switchType: Switch
    private lateinit var controller:EnterDataController
    private lateinit var cancelBtn:Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_data)

        init()
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener(saveRecord)



    }
    fun init(){
        controller = EnterDataController(this)
        cancelBtn = findViewById(R.id.cancelBtn)
        remarkEditBox = findViewById(R.id.editRemark)
        editTextDate = findViewById<TextView>(R.id.editTextDate)
        toolbar = findViewById(R.id.enter_toolbar)
        editTextNumber = findViewById(R.id.editTextNumber)
        editTextType = findViewById(R.id.editType)
        editTextSubType = findViewById(R.id.editSubType)
        switchType = findViewById(R.id.switch1)
        editTextNumber.setOnClickListener(editNumber)
        editTextDate.setOnClickListener(editDate)
        editTextType.setOnClickListener(editType)
        cancelBtn.setOnClickListener(onDetory)

        incomeExpenseSwitch()
        setCalendartoToday()

        //工具列，設置返回鍵啟用

    }
    //收入支出切換
    fun incomeExpenseSwitch(){
        switchType.setOnCheckedChangeListener{ _, isCheck->
            if(isCheck) {//如果按開關，可以用此按鈕來改變是收入還是支出(若之後要編輯記帳紀錄可以用個TAG之類的東西紀錄他是支出還是收入，在Oncreate的時候就對switch按鍵進行變動)
                toolbar.title = "收入"
                controller.setStateToIncome()
            }else{
                toolbar.title = "支出"
                controller.setStateToExpense()
            }
        }
    }

    fun setCalendartoToday(){//設定今天的日期
        editTextDate.text = "${mYear}/${mMonth+1}/${mDay}"
    }

    //返回鍵
    override fun onBackPressed() {
        super.onBackPressed()
        val ft = supportFragmentManager.beginTransaction()
        hideFragment(ft)
        ft.commit()
    }

    //toolbar set
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
                finish()
        }
        return super.onOptionsItemSelected(item)
    }


    var saveRecord = View.OnClickListener{

        val date = editTextDate.text.toString()
        val amount = editTextNumber.text.toString().toInt()
        val cate = editTextType.text.toString()
        val subCate = editTextSubType.text.toString()
        val remark = remarkEditBox.text.toString()

        val result = controller.saveRecord(date,amount,cate,subCate,remark)

        if (result){
            editTextDate.text = ""
            editTextNumber.text = ""
            editTextType.text = ""
            editTextSubType.text = ""
            remarkEditBox.text = ""
            makeToast("成功新增一筆紀錄")
        }else{
            makeToast("新增紀錄失敗，請確認輸入資料無誤")
        }
    }

    fun makeToast(msg:String){
        Toast.makeText(this,msg.toString(),Toast.LENGTH_SHORT).show()
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
        editTextType.text = "伙食費"
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

    var onDetory = View.OnClickListener{
        finish()
    }

}
