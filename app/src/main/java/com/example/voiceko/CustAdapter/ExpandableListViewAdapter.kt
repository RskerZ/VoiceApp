package com.example.voiceko.CustAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.voiceko.R
import org.w3c.dom.Text

class ExpandableListViewAdapter(private val context: Context,
                                private val Date: List<String>,
                                private val Records: List<List<String>>): BaseExpandableListAdapter() {
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
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_record, null)
        val type = view.findViewById<TextView>(R.id.txtRecord_typename)
        val amount = view.findViewById<TextView>(R.id.txtRecord_amount)
        val subtypeRemark = view.findViewById<TextView>(R.id.txtRecord_subtype_remark)
        type.text = Records[groupPosition][childPosition]

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}