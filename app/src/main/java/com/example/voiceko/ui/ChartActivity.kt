package com.example.voiceko.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.voiceko.ChartPagerAdapter
import com.example.voiceko.R
import com.google.android.material.tabs.TabLayout
import java.util.*

class ChartActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        toolbar = findViewById(R.id.chart_toolbar)
        // 設定右上角的 menu
        toolbar.inflateMenu(R.menu.selectmonth);


        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "圖表分析(${mYear}年${mMonth + 1}月)"
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
        var adapter =
            ChartPagerAdapter(supportFragmentManager)
        var viewpager:ViewPager = findViewById(R.id.viewPager)
        viewpager.adapter = adapter

        var tabchart:TabLayout = findViewById(R.id.tabChart)
        tabchart.setupWithViewPager(viewpager)
    }
}