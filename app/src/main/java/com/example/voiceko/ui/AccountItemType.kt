package com.example.voiceko.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.voiceko.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_accounttype.*

class AccountItemType(var actname: String): Fragment(){
    private var editTextType:TextView? = null
    //分類可以在這邊做動態載入
    private var type = arrayOf(
        "google", "facebook", "github", "instagram", "flicker", "twitter",
        "pinterest", "部落格", "line", "wordpress", "homepage", "yahoo", "pchome", "pazzo", "youtube"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_accounttype, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(actname) {
            "Enter" -> editTextType = activity!!.findViewById(R.id.editType)
            "Fixedcost" -> editTextType = activity!!.findViewById(R.id.fixedcost_editType)
        }
        //分類的載入
        val items = ArrayList<Map<String, Any>>()
        for(i in type.indices){
            val item = HashMap<String, Any>()
            item["text"] = type[i]
            items.add(item)
        }
        val adapter = SimpleAdapter(activity, items, R.layout.type, arrayOf("text"), intArrayOf(R.id.typetext))
        typeGrid.adapter = adapter
        typeGrid.onItemClickListener = AdapterView.OnItemClickListener {
                _, _, position, _ ->
            editTextType?.text = type[position]
        }

        //新增按鈕
        addTypeBtn.setOnClickListener{
            Log.e("AAA","新增起來")
            var intent = Intent(activity, AddTypeActivity::class.java)
            startActivity(intent)
        }
    }
}