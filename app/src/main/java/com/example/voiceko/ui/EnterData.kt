package com.example.voiceko.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.Html
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.Controller.RecordController
import com.example.voiceko.R
import com.example.voiceko.Record
import kotlinx.android.synthetic.main.activity_enter_data.*
import java.util.*


class EnterData : AppCompatActivity() {
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var mDay = c.get(Calendar.DAY_OF_MONTH)
    val lilcaculater: Fragment = LilCaculater("Enter")
    val accItem: Fragment = AccountItemType("Enter")
    val accSubItem: Fragment = SubItemType("Enter")
    private lateinit var editTextDate: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var editTextNumber: TextView
    private lateinit var editTextType: TextView
    private lateinit var editTextSubType: TextView
    private lateinit var remarkEditBox:TextView
    private lateinit var switchType: Switch
    private lateinit var controller:EnterDataController
    private lateinit var recordController: RecordController
    private lateinit var cancelBtn:Button
    private var recordID = -1







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContentView(R.layout.activity_enter_data)

        init()

        if (intent.hasExtra(EXTRA_MESSAGE)){
            val id =intent.getStringExtra(EXTRA_MESSAGE)
            id?.let {
                recordController = RecordController.instance
                recordController.init(this)
                recordID = recordController.setRecordInfoToEnterData(this,id)
                saveBtn.text = "修改"
                cancelBtn.text = "刪除"
            }
        }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        if (recordID > -1){
            saveBtn.setOnClickListener(updateRecord)
            cancelBtn.text = "刪除"
        }else{
            saveBtn.setOnClickListener(saveRecord)
        }





    }
    fun init(){
        controller = EnterDataController.instance
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
        editTextSubType.setOnClickListener(editSubType)
        remarkEditBox.setOnClickListener(editRemark)
        cancelBtn.setOnClickListener(onDetory)
        controller.init(this)
        incomeExpenseSwitch()
        setCalendartoToday()
        //工具列，設置返回鍵啟用
    }
    fun setDate(date:String){
        editTextDate.text = date
    }
    fun setAmount(amount:String){
        editTextNumber.text = amount
    }
    fun setCate(cate:String){
        editTextType.text = cate
    }
    fun setSubCate(subCate:String){
        editTextSubType.text = subCate
    }
    fun setRemark(remark:String){
        remarkEditBox.text=remark
    }
    fun setType(type: String){
        if (type == "收入"){
            switchType.isChecked = true
            controller.setStateToIncome()
        }

    }
    //收入支出切換
    fun incomeExpenseSwitch(){
        val ft = supportFragmentManager.beginTransaction()
        switchType.setOnCheckedChangeListener{ _, isCheck->
            if(isCheck) {//如果按開關，可以用此按鈕來改變是收入還是支出(若之後要編輯記帳紀錄可以用個TAG之類的東西紀錄他是支出還是收入，在Oncreate的時候就對switch按鍵進行變動)
                toolbar.title = "收入"
                controller.setStateToIncome()
            }else{
                toolbar.title = "支出"
                controller.setStateToExpense()
            }
            editTextType.text=""
            if (accItem.isVisible){
                showFragment("acc")
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
        val record = Record(date,amount,cate,subCate, remark)

        val result = controller.saveRecord(record)

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

    var updateRecord = View.OnClickListener{

        val date = editTextDate.text.toString()
        val amount = editTextNumber.text.toString().toInt()
        val cate = editTextType.text.toString()
        val subCate = editTextSubType.text.toString()
        val remark = remarkEditBox.text.toString()
        val record = Record(date,amount,cate,subCate, remark)

        val result = controller.updateRecord(recordID,record)

        if (result){
            editTextDate.text = ""
            editTextNumber.text = ""
            editTextType.text = ""
            editTextSubType.text = ""
            remarkEditBox.text = ""
            makeToast("成功更新一筆紀錄")
        }else{
            makeToast("新增紀錄失敗，請確認輸入資料無誤")
        }
    }

    fun makeToast(msg: String){
        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show()
    }
    //日期選擇器
    private var editDate = View.OnClickListener {
        val ft = supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        hideFragment(ft)
        ft.commit()
        DatePickerDialog(this, { _, mYear, mMonth, mDay ->
            run {
                val format = setDateFormat(mYear, (mMonth+1), mDay)
                editTextDate.text = format
            }
        }, mYear, mMonth, mDay).show()
    }

    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year/$month/$day"
    }

    //計算機
    private var editNumber = View.OnClickListener {
        editTextNumber.text = ""
        showFragment("lil")

    }
    private var editType = View.OnClickListener {
        showFragment("acc")
    }
    private var editSubType = View.OnClickListener {
        setEditText(editTextSubType)
        showFragment("subacc")
    }
    private var editRemark = View.OnClickListener {
        setEditText(remarkEditBox)

        val ft = supportFragmentManager.beginTransaction()
        hideFragment(ft)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        ft.commit()
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {//關閉鍵盤
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        //EditText初始化
        resetEditText(editTextSubType)
        resetEditText(remarkEditBox)

        return super.dispatchTouchEvent(ev)
    }


    //function
    fun showFragment(f: String){
        val ft = supportFragmentManager.beginTransaction()
        hideFragment(ft)
        when(f){
            "lil" -> {
                if (lilcaculater.isAdded) {
                    ft.show(lilcaculater)
                }else {
                    ft.add(R.id.fragment_container,lilcaculater)
                }
            }
            "acc" ->{
                if(accItem.isAdded){
                    ft.detach(accItem)
                    ft.attach(accItem)
                    ft.show(accItem)
                } else {
                    ft.add(R.id.fragment_container, accItem)
                }
            }
            "subacc" -> {
                if (accSubItem.isAdded) {
                    ft.detach(accSubItem)
                    ft.attach(accSubItem)
                    ft.show(accSubItem)
                } else {
                    ft.add(R.id.fragment_container, accSubItem)
                }
            }
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
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
    private fun setEditText(ediText: TextView){
        ediText.isFocusable = true
        ediText.isFocusableInTouchMode = true
        ediText.requestFocus()
        ediText.requestFocusFromTouch()

        val inputmanager = ediText.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputmanager?.showSoftInput(ediText, 0)
    }
    private fun resetEditText(ediText: TextView){
        ediText.isFocusable = false
        ediText.isFocusableInTouchMode = false
        ediText.requestFocus()
        ediText.requestFocusFromTouch()
    }


    var onDetory = View.OnClickListener{
        if (recordID > 0){
            AlertDialog.Builder(this)
                .setTitle("刪除紀錄")
                .setMessage("確定要刪除此筆紀錄嗎?")
                .setPositiveButton("確定"){dialog, which->
                    finish()
                    controller.deleteRecord(recordID)
                }
                .setNegativeButton("取消"){dialog, which->
                }.show()

        }else{
            finish()
        }

    }

}
