package com.example.voiceko.chartFragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.example.voiceko.R
import java.util.*


class ExpendChart: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_expend_chart, container, false)
        return view
        Log.e("lil","onCreateView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year/${month + 1}/$day"
    }
}