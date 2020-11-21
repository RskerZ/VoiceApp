package com.example.voiceko.ui
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.voiceko.Controller.PeriodRecordsController
import com.example.voiceko.R
import java.util.*
import kotlin.collections.ArrayList

class FixCostActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener{
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var mDay = c.get(Calendar.DAY_OF_MONTH)
    val lilcaculater: Fragment = LilCaculater("Fixedcost")
    val accItem: Fragment = AccountItemType("Fixedcost")
    val accSubItem: Fragment = SubItemType("Fixedcost")
//    val cycleTimeHours = arrayListOf<Long>(24, 168, 732, 8766)
    val cycleTimeHours = arrayListOf<Long>(15, 20, 45, 111)
    var hours = 24.toLong()
    private lateinit var toolbar: Toolbar
    private lateinit var editTextDate: TextView
    private lateinit var editTextNumber: TextView
    private lateinit var editTextType: TextView
    private lateinit var editTextSubType: TextView
    private lateinit var remarkEditBox:TextView
    private lateinit var switchType: Switch
    private lateinit var cycleTimeSpinner: Spinner
    private lateinit var controller:PeriodRecordsController
    private lateinit var saveBtn : Button
    private lateinit var cancelBtn : Button
    private var workID:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContentView(R.layout.activity_fix_cost)


        init()
        //設定重複週期下拉選單
        val cycletimeList = arrayListOf<String>("每天","每週","每月","每年")
        var cycleTimeAdapter = ArrayAdapter<String>(this, R.layout.cycletime_spinner, cycletimeList)
        cycleTimeSpinner.adapter = cycleTimeAdapter
        cycleTimeSpinner.onItemSelectedListener = this

        if(intent.hasExtra(EXTRA_MESSAGE)){
            this.workID = intent.getStringExtra(EXTRA_MESSAGE)
            this.workID?.let {
                controller.setRecordInfoToFixCostActivity(this,workID!!)

            }
            //TODO Change Button Image
        }






        editTextNumber.setOnClickListener {
            editTextNumber.text = ""
            showFragment("lil")
        }
        editTextDate.setOnClickListener {
           datePicker()
        }
        editTextType.setOnClickListener {
            showFragment("acc")
        }
        editTextSubType.setOnClickListener {
            setEditText(editTextSubType)
            showFragment("subacc")
        }


        //收入支出切換
        switchType.setOnCheckedChangeListener{ _, isCheck->
            if(isCheck) {//如果按開關，可以用此按鈕來改變是收入還是支出(若之後要編輯記帳紀錄可以用個TAG之類的東西紀錄他是支出還是收入，在Oncreate的時候就對switch按鍵進行變動)
                toolbar.setTitle("固定收入")
                controller.changeTypeToIncome()
            }else{
                toolbar.setTitle("固定支出")
                controller.changeTypeToExpense()
            }
            editTextType.text=""
            if (accItem.isVisible){
                showFragment("acc")
            }
        }
        saveBtn.setOnClickListener {
            savePeriodRecord(workID)
            finish()
        }
        cancelBtn.setOnClickListener {
            workID?.let {
                AlertDialog.Builder(this)
                    .setTitle("刪除固定支出")
                    .setMessage("確定要刪除此固定支出嗎?")
                    .setPositiveButton("確定"){dialog, which->
                        finish()
                        controller.cancelPeriodWork(workID!!)
                    }
                    .setNegativeButton("取消"){dialog, which->
                    }.show()
            }?:finish()

        }

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }



    private fun init(){

        saveBtn = findViewById(R.id.fixedcost_saveBtn)
        cancelBtn = findViewById(R.id.fixedcost_cancelBtn)
        editTextDate = findViewById<TextView>(R.id.fixedcost_editTextDate)
        toolbar = findViewById(R.id.fixedcost_toolbar)
        editTextNumber = findViewById(R.id.fixedcost_editTextNumber)
        editTextType = findViewById(R.id.fixedcost_editType)
        editTextSubType = findViewById(R.id.fixedcost_editSubType)
        switchType = findViewById(R.id.fixedcost_switch)
        remarkEditBox = findViewById(R.id.fixedcost_editRemark)
        remarkEditBox.setOnClickListener(editRemark)
        cycleTimeSpinner = findViewById(R.id.cycleTimeSpinner)
        controller = PeriodRecordsController.instance
        controller.init(this)
        setDayToToday()

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
    fun setCycle(hours:String){
        cycleTimeSpinner.setSelection(cycleTimeHours.indexOf(hours.toLong()))
    }
    fun setRemark(remark:String){
        remarkEditBox.text = remark
    }
    fun setSwitch(type:String){
        if (type == "收入"){
            switchType.isChecked = true
            controller.changeTypeToIncome()
        }
    }
    private fun savePeriodRecord(workID:String? = null){
        val date = editTextDate.text.toString()
        val amount = editTextNumber.text.toString().toInt()
        val cate = editTextType.text.toString()
        val subCate = editTextSubType.text.toString()
        val remark = remarkEditBox.text.toString()
        controller.savePeriodWork(date,amount,cate,subCate,remark,hours,workID)
    }
    private fun setDayToToday(){
        editTextDate.text = "${mYear}/${mMonth+1}/${mDay}"
    }
    private fun datePicker(){
        DatePickerDialog(this, { _, mYear, mMonth, mDay ->
            run {
                val format = "${setDateFormat(mYear, mMonth, mDay)}"
                editTextDate.text = format
            }
        }, mYear, mMonth, mDay).show()
    }
    private var editRemark = View.OnClickListener {
        setEditText(remarkEditBox)

        val ft = supportFragmentManager.beginTransaction()
        hideFragment(ft)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        ft.commit()
    }
    //當你案其他地方的時候做的事情
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {//關閉鍵盤
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        //不讓輸入跑出來
        resetEditText(remarkEditBox)
        resetEditText(editTextSubType)

        return super.dispatchTouchEvent(ev)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val ft = supportFragmentManager.beginTransaction()
        hideFragment(ft)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
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
    private fun showFragment(f: String){
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
                    ft.detach(accItem)
                    ft.attach(accItem)
                    ft.show(accItem)
                } else {
                    ft.add(R.id.fragment_container, accItem)
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
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.addToBackStack(null)
        ft.commit()
    }
    private fun hideFragment(ft: FragmentTransaction){
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
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        hours = cycleTimeHours[position]
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

