package com.example.voiceko.CustAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.R


class EditTypeListAdapter(context: Context, itemList: List<String>,weightList:List<Int>): BaseAdapter() {
    val mLatInf: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val mItemList = itemList
    val mWeight = weightList
    lateinit var controller:EnterDataController
    private var count = mWeight.sum()
        //要新增到ListView的東西請放這裡
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = mLatInf.inflate(R.layout.type_listitem,parent,false)
        val typeNameTextView = view.findViewById<TextView>(R.id.typeNameTextView)
        val typeFavorite = view.findViewById<CheckBox>(R.id.typeFavorite)
        controller = EnterDataController.instance
        typeNameTextView.text = mItemList[position]
        if (mWeight[position] != 1){
            typeFavorite.isChecked = true
        }
        //設定哪個是最愛
        typeFavorite.setOnClickListener {
            if(typeFavorite.isChecked){
                controller.changeCategoryWeight(count,position)
                count--
                Log.e("TEST","$count")
            }else{
                controller.changeCategoryWeight(1,position)
                count++
                Log.e("TEST","已移除我的最愛")
            }
        }
        return view
    }

    override fun getItem(position: Int): Any? {
        return mItemList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mItemList.size
    }

}