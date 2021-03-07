package com.example.voiceko.ui
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Bundle
import android.provider.AlarmClock
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.Controller.RecordController
import com.example.voiceko.CustAdapter.ExpandableListViewAdapter
import com.example.voiceko.DayNotifyReceiver.NightNotify
import com.example.voiceko.DayNotifyReceiver.NoonNotify
import com.example.voiceko.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    var CTR = EnterDataController.instance
    private lateinit var incomeText: TextView
    private lateinit var payText: TextView
    private lateinit var totalText: TextView
    private lateinit var recordList: ExpandableListView
    private lateinit var toolbar: Toolbar
    private lateinit var controller:RecordController
    private lateinit var recordListAdapter:ExpandableListViewAdapter
    val c: Calendar = Calendar.getInstance()

    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    private lateinit var recordlist :ArrayList<ArrayList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        checkAudioPremisson()
        // 設定右上角的 menu
        toolbar.inflateMenu(R.menu.selectmonth)
        toolbar.setOnClickListener(editDate)
        toolbar.setOnMenuItemClickListener(selectMonthListener)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.menuBtn)
        bottomNavigationView.menu.setGroupCheckable(0, false, false)
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomBarListener)
        recordList.setOnChildClickListener{ parent, v, groupPosition, childPosition, id ->
            val recordID = recordListAdapter.getrecordID(groupPosition,childPosition)
            val intent = Intent(this,EnterData::class.java).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, recordID)
            }
            startActivity(intent)
            true
        }



        noonNotify()
        nightNotify()
        createShortCuts()
        //載入記帳紀錄
        loadInfo()


    }
    fun noonNotify(){
        val alarmCal = Calendar.getInstance()
        alarmCal.set(Calendar.HOUR_OF_DAY, 13)
        alarmCal.set(Calendar.MINUTE, 0)
        alarmCal.set(Calendar.SECOND,0)
        val intentAlarm = Intent(this, NoonNotify::class.java)
        intentAlarm.addCategory("GoodAfternoon")
        val pi = PendingIntent.getBroadcast(this, 1 , intentAlarm, PendingIntent.FLAG_ONE_SHOT)
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.RTC_WAKEUP,alarmCal.timeInMillis,1000*60*60*24, pi)

    }
    fun nightNotify(){
        val alarmCal = Calendar.getInstance()
        alarmCal.set(Calendar.HOUR_OF_DAY, 19)
        alarmCal.set(Calendar.MINUTE, 0)
        alarmCal.set(Calendar.SECOND,0)
        val intentAlarm = Intent(this, NightNotify::class.java)
        intentAlarm.addCategory("GoodNight")
        val pi = PendingIntent.getBroadcast(this, 2 , intentAlarm, PendingIntent.FLAG_ONE_SHOT)
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.RTC_WAKEUP,alarmCal.timeInMillis,1000*60*60*24, pi)
    }

    fun createShortCuts(){
        val shortcutManager = getSystemService<ShortcutManager>(ShortcutManager::class.java)
        val intent = Intent(this,VoiceActivity::class.java)
        intent.action = Intent.ACTION_VIEW
        val shortcut = ShortcutInfo.Builder(this, "VoiceShortCuts")
            .setIcon(Icon.createWithResource(this,R.drawable.voko))
            .setShortLabel("語音記帳")
            .setLongLabel("開始語音記帳")
            .setIntent(intent)
            .build()

        shortcutManager!!.dynamicShortcuts = Arrays.asList(shortcut)
    }

    override fun onRestart() {
        super.onRestart()
        loadInfo()
    }
    private fun checkAudioPremisson(){
        val permissioncheck = ContextCompat.checkSelfPermission(this,android.Manifest.permission.RECORD_AUDIO)
        if (permissioncheck != PackageManager.PERMISSION_GRANTED){
            askMicPermission()
        }
    }

    private fun askMicPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), 100)
    }



    var selectMonthListener= Toolbar.OnMenuItemClickListener{
        when(it.itemId){
            R.id.rightBtn -> {
                nextMonth()
                loadInfo()
                true
            }
            R.id.leftBtn -> {
                lastMonth()
                loadInfo()
                true
            }
            else ->{
                true
            }
        }
    }

    var bottomBarListener = BottomNavigationView.OnNavigationItemSelectedListener{ item: MenuItem ->
        when (item.itemId) {
            R.id.addMenu -> goEnter()
            R.id.micMenu -> {
                //Toast.makeText(this, "你好我會語音辨識", Toast.LENGTH_SHORT).show()
                goVoice()
            }
            R.id.reportMenu -> goChart()
            R.id.setMenu -> goSetting()
        }
        true
    }

    private var editDate = View.OnClickListener {

        var mDay=15

        DatePickerDialog(this, { _, mYear, mMonth, D ->
            run {
                this.mMonth = mMonth
                this.mYear = mYear
                mDay = D
                loadInfo()
            }
        }, mYear, mMonth, mDay).show()
    }


    fun nextMonth(){
        if (mMonth == 11){
            mMonth = 0
            mYear++
        }else{
            mMonth++
        }
    }

    fun lastMonth(){
        if (mMonth == 0){
            mMonth = 11
            mYear--
        }else{
            mMonth--
        }
    }


    private fun init(){
        controller = RecordController.instance
        controller.init(this)
        incomeText = findViewById(R.id.incomeText)
        payText = findViewById(R.id.payText)
        totalText = findViewById(R.id.totalText)
        recordList = findViewById(R.id.record)
        toolbar = findViewById(R.id.main_toolbar)
    }

    fun loadInfo(){
        recordListAdapter = controller.loadRecordList(mYear, mMonth+1)
        toolbar.title = "${mYear}年${mMonth + 1}月"
        setRecord(recordListAdapter)
        val income = controller.getIncome()
        val expand = controller.getExpand()
        incomeText.text = income.toString()
        payText.text = expand.toString()
        totalText.text = (income-expand).toString()
    }

    //切換Activity
    fun goChart(){
        var intent = Intent(this, ChartActivity::class.java)
        startActivity(intent)
    }
    fun goEnter(){
        var intent = Intent(this, EnterData::class.java)
        startActivity(intent)
    }
    fun goSetting(){
        var intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }
    fun goVoice(){
        var intent = Intent(this, VoiceActivity::class.java)
        startActivity(intent)
    }
    fun makeToast(msg: String){
        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show()
    }
    //載入記帳紀錄資料
    private fun setRecord(adapter: ExpandableListViewAdapter){
//        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recordData)
        recordList.setAdapter(adapter)
    }

}

