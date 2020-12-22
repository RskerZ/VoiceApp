package com.example.voiceko.CustAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.example.voiceko.R

class ExpandableListViewAdapter(private val context: Context,
                                private val Date: List<String>,
                                private val Records: List<List<MutableMap<String,String>>>): BaseExpandableListAdapter() {
        override fun getGroupCount(): Int {
        return Date.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
       return Records[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): Any {
        return Date[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return Records[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return (groupPosition * 100 + childPosition).toLong()
    }

    fun getrecordID(groupPosition: Int, childPosition: Int):String{
        return Records[groupPosition][childPosition].get("id")!!
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_recorddate, null)
        val textView = view.findViewById<TextView>(R.id.txtRecordDate)
        textView.text = Date[groupPosition]
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val record = Records[groupPosition][childPosition]
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_record, null)
        val typeTextView = view.findViewById<TextView>(R.id.txtRecord_Cate)
        val amountTextView = view.findViewById<TextView>(R.id.txtRecord_amount)
        val subtypeRemarkTextView = view.findViewById<TextView>(R.id.txtRecord_subtype_remark)
        val recordType = view.findViewById<TextView>(R.id.txtRecordType)
        val amount = record.get("amount")
        val type = record.get("type")
        if (record.get("subCate")!!.isNotEmpty()){
            typeTextView.text = record.get("subCate")
            subtypeRemarkTextView.text =record.get("cate")
        }else{
            typeTextView.text =record.get("cate")
            subtypeRemarkTextView.text=""
        }

        amountTextView.text = context.getString(R.string.dollarSign,amount)
//        dayTextView.text =  Records[groupPosition][childPosition].get("day")
        if (type == "支出"){
            recordType.foreground = getDrawable(context,R.drawable.down_cost)
        }else{
            recordType.foreground = getDrawable(context,R.drawable.up_cost)
        }

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}