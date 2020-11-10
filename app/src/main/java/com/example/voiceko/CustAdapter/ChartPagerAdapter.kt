package com.example.voiceko.CustAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.voiceko.chartFragment.ExpendChart
import com.example.voiceko.chartFragment.IncomeChart


class ChartPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {//要導入的片段介面
        when(position){
            0-> return ExpendChart()
            else-> return IncomeChart()
        }
    }

    override fun getCount(): Int {//切換的頁面數量
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {//TAB的文字
        when(position){
            0-> return "支出類別圖"
            else-> return "收入類別圖"
        }
    }


}