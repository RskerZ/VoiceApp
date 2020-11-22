package com.example.voiceko.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.voiceko.Controller.CharController
import com.example.voiceko.CustAdapter.ChartPagerAdapter
import com.example.voiceko.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_chart.*
import java.util.*

class ChartActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var viewpager: ViewPager2
    private var controller = CharController.instance
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)+1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        toolbar = findViewById(R.id.chart_toolbar)
        // 設定右上角的 menu
        toolbar.inflateMenu(R.menu.selectmonth);

        controller.init(this,mYear.toString(),mMonth.toString())

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setOnMenuItemClickListener(selectMonthListener)

        initViewPager()
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

    private fun initViewPager(){
        toolbar.title = "圖表分析(${mYear}年${mMonth}月)"
        var adapter =
            ChartPagerAdapter(this)
        viewpager = findViewById(R.id.viewPager)

        viewpager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabChart)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0-> tab.text = "支出"
                1-> tab.text = "收入"
            }
        }.attach()
    }

    var selectMonthListener= Toolbar.OnMenuItemClickListener{
        when(it.itemId){
            R.id.rightBtn -> {
                nextMonth()
                initViewPager()
                true
            }
            R.id.leftBtn -> {
                lastMonth()
                initViewPager()
                true
            }
            else ->{
                true
            }
        }
    }
    fun nextMonth(){
        if (mMonth == 12){
            mMonth = 1
            mYear++
            controller.setYear(mYear.toString())
        }else{
            mMonth++
        }
        controller.setMonth(mMonth.toString())
    }

    fun lastMonth(){
        if (mMonth == 1){
            mMonth = 12
            mYear--
            controller.setYear(mYear.toString())
        }else{
            mMonth--
        }
        controller.setMonth(mMonth.toString())
    }

}