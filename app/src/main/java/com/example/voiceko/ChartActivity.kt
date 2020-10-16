package com.example.voiceko
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class ChartActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        toolbar = findViewById(R.id.chart_toolbar)
        // 設定右上角的 menu
        toolbar.inflateMenu(R.menu.lilmenu);
        toolbar.setOnMenuItemClickListener{

        }
        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initViewPager()
    }

    //返回鍵
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewPager(){
        var adapter = ChartPagerAdapter(supportFragmentManager)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var viewpager:ViewPager = findViewById(R.id.viewPager)
        viewpager.adapter = adapter

        var tabchart:TabLayout = findViewById(R.id.tabChart)
        tabchart.setupWithViewPager(viewpager)
    }
}