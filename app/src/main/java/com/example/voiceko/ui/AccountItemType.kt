package com.example.voiceko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_accounttype.*

class AccountItemType(var actname: String): Fragment(){
    private lateinit var controller:EnterDataController;
    private var editTextType:TextView? = null
    //分類可以在這邊做動態載入
    private lateinit var  type : ArrayList<String>


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


        //新增按鈕
        addTypeBtn.setOnClickListener{
            addTypeDialog().show()
        }
    }

    override fun onStart() {
        super.onStart()
        controller = EnterDataController.instance
        loadList()
    }


    private fun loadList(){
        var items = ArrayList<Map<String, Any>>()
        type = controller.loadCateList()
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
            activity!!.onBackPressed()
        }
    }

    //新增對話方塊
    private fun addTypeDialog(): AlertDialog {
        val dialogView = layoutInflater.inflate(R.layout.addtype_dialog,null)
        val newItemName = dialogView.findViewById<EditText>(R.id.addTypeName)
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("新增類別")
        builder.setView(dialogView)
        builder.setPositiveButton("新增") { dialog, id ->
            val newCate = newItemName.text.toString()
            var msg:String
            if (newCate.length == 0){
                msg = "請輸入欲新增的分類名稱"
            }else{
                val result = controller.insertNewCate(newCate)
                val type = controller.getType()
                if (result == 200){
                    msg = "以新增${newCate}至${type}"
                }else if (result == 300){
                    msg = "${type}已經存在:${newCate}，勿重複新增"
                }else{
                    msg = "分類新增失敗"
                }
            }
            Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
        }
        val dialog = builder.create()
        return dialog
    }
}