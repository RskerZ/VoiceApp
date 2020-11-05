package com.example.voiceko

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class EditTypeListAdapter(context: Context, itemList: List<String>): BaseAdapter() {
    val mLatInf: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    val mItemList = itemList

    //要新增到ListView的東西請放這裡
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = mLatInf.inflate(R.layout.type_listitem,parent,false)
        val typeNameTextView = view.findViewById<TextView>(R.id.typeNameTextView)
        val typeFavorite = view.findViewById<CheckBox>(R.id.typeFavorite)
        typeNameTextView.text = mItemList[position]
        //設定哪個是最愛
        typeFavorite.setOnClickListener {
            if(typeFavorite.isChecked){
                //Toast.makeText(this, "已新增到我的最愛", Toast.LENGTH_SHORT).show()
                Log.e("AAAAA","已新增到我的最愛")
            }else{
                //Toast.makeText(this, "已移除我的最愛", Toast.LENGTH_SHORT).show()
                Log.e("AAAAA","已移除我的最愛")
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