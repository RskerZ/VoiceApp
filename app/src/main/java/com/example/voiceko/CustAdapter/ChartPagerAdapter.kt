package com.example.voiceko.CustAdapter

import androidx.fragment.app.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.voiceko.chartFragment.PipChartFragment


class ChartPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {//要導入的片段介面
        when(position){
            0-> {
                return PipChartFragment.newInstance("支出")
            }
            else-> {
                return PipChartFragment.newInstance("收入")
            }
        }
    }

    override fun getItemCount(): Int {//切換的頁面數量
        return 2
    }







}