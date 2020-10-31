package com.example.voiceko

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_accounttype.*

class AccountItemType(var actname: String): Fragment(){
    private var editTextType:TextView? = null
    private var addTypeBtn:FloatingActionButton? = null
    var type1: TextView? = null
    var type2: TextView? = null
    var type3: TextView? = null
    var type4: TextView? = null
    var type5: TextView? = null
    var type6: TextView? = null
    var type7: TextView? = null
    var type8: TextView? = null
    var type9: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_accounttype, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTypeBtn = activity!!.findViewById(R.id.addTypeBtn)
        when(actname) {
            "Enter" -> editTextType = activity!!.findViewById(R.id.editType)
            "Fixedcost" -> editTextType = activity!!.findViewById(R.id.fixedcost_editType)
        }
        type1 = activity!!.findViewById(R.id.type1)
        type2 = activity!!.findViewById(R.id.type2)
        type3 = activity!!.findViewById(R.id.type3)
        type4 = activity!!.findViewById(R.id.type4)
        type5 = activity!!.findViewById(R.id.type5)
        type6 = activity!!.findViewById(R.id.type6)
        type7 = activity!!.findViewById(R.id.type7)
        type8 = activity!!.findViewById(R.id.type8)
        type9 = activity!!.findViewById(R.id.type9)

        type1?.setOnClickListener(showtype)
        type2?.setOnClickListener(showtype)
        type3?.setOnClickListener(showtype)
        type4?.setOnClickListener(showtype)
        type5?.setOnClickListener(showtype)
        type6?.setOnClickListener(showtype)
        type7?.setOnClickListener(showtype)
        type8?.setOnClickListener(showtype)
        type9?.setOnClickListener(showtype)
        //新增分類按鈕
        addTypeBtn?.setOnClickListener{
            Log.e("acc","77777777777777777777777777777")
        }
    }
    private var showtype = View.OnClickListener{
        when(it){
            type1->editTextType?.text = type1?.text
            type2->editTextType?.text = type2?.text
            type3->editTextType?.text = type3?.text
            type4->editTextType?.text = type4?.text
            type5->editTextType?.text = type5?.text
            type6->editTextType?.text = type6?.text
            type7->editTextType?.text = type7?.text
            type8->editTextType?.text = type8?.text
            type9->editTextType?.text = type9?.text

        }
        activity!!.onBackPressed()
    }
}