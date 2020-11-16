package com.example.voiceko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.CustAdapter.EditTypeListAdapter
import com.example.voiceko.R
import java.text.FieldPosition


class EditTypeActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var typeList: ListView
    private lateinit var savebtn: Button
    private lateinit var typeAdapter:EditTypeListAdapter
    private lateinit var controller:EnterDataController
    private var typeListData = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_type)
        typeList = findViewById(R.id.typeList)
//        savebtn = findViewById(R.id.editTypeSaveBtn)
        toolbar = findViewById(R.id.edittype_toolbar) // 設定右上角的 menu

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        controller = EnterDataController.instance
        controller.init(this)
        loadList()

        //ItemClick
        typeList.setOnItemClickListener{parent, view, position, id ->
            val element = typeList.adapter.getItem(position) // The item that was clicked
            EditTypeDialog(element.toString(),position).show()
        }

    }
    fun loadList(){
        typeListData = controller.loadCateList()
        val cateWeights = controller.loadCateWeight()
        typeAdapter = EditTypeListAdapter(this,typeListData,cateWeights)
        typeList.adapter = typeAdapter
    }
    //返回鍵 AND 右上收入支出的切換
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.costtype -> controller.setStateToExpense()
            R.id.incometype -> controller.setStateToIncome()
            else -> finish()
        }
        loadList()
        return super.onOptionsItemSelected(item)
    }
    //創造menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.typemenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //對話方塊
    private fun EditTypeDialog(typeName: String,position:Int): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("編輯類別")
        //設定對話方塊內部元件 類似在OnCreate()裡面
        val inflater = LayoutInflater.from(application)
        val view: View = inflater.inflate(R.layout.edittype_dialog, null)
        builder.setView(view)
        val editTypeName = view.findViewById<EditText>(R.id.editTypeName)

        editTypeName.setText(typeName)


        var msg = ""
        builder.setNeutralButton("取消"){ _, _ ->

        }
        builder.setPositiveButton("編輯") { _, _ ->
            val newName = editTypeName.text.toString()
            val result = controller.changeCategoryName(newName,position)

            if (result){
                msg = "成功修改類別名稱"
                loadList()
            }else{
                msg = "修改失敗"
            }
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("刪除") { _, _ ->
            val result = controller.deleteCategory(position)
            if (result){
                msg = "成功刪除類別"
                loadList()
            }else{
                msg = "刪除失敗"
            }
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()

        }
        val dialog = builder.create()


        return dialog
    }
}


