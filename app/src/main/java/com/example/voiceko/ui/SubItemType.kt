package com.example.voiceko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.voiceko.R
import kotlinx.android.synthetic.main.fragment_subtype.*

class SubItemType(var actname: String): Fragment() {
    private var editTextSubType: TextView? = null
    //子分類可以在這邊做載入
    private var subtype = arrayOf(
        "早餐", "午餐", "晚餐", "飲料", "零食", "餅乾",
        "點心", "糖果", "口香糖"
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_subtype, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(actname) {
            "Enter" -> editTextSubType = activity!!.findViewById(R.id.editSubType)
            "Fixedcost" -> editTextSubType = activity!!.findViewById(R.id.fixedcost_editSubType)
        }

        //分類的載入
        val items = ArrayList<Map<String, Any>>()
        for(i in subtype.indices){
            val item = HashMap<String, Any>()
            item["text"] = subtype[i]
            items.add(item)
        }
        val adapter = SimpleAdapter(activity, items, R.layout.type, arrayOf("text"), intArrayOf(R.id.typetext))
        subtypeGrid.adapter = adapter
        subtypeGrid.onItemClickListener = AdapterView.OnItemClickListener {
                _, _, position, _ ->
            editTextSubType?.text = subtype[position]
            activity!!.onBackPressed()
        }
    }
}