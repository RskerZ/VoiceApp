package com.example.voiceko.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.EditTypeListAdapter
import com.example.voiceko.R


class EditTypeActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var typeList: ListView
    private lateinit var savebtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_type)
        typeList = findViewById(R.id.typeList)
        savebtn = findViewById(R.id.editTypeSaveBtn)
        //類別資料  Oncreate預設要是支出類別
        var typeListData = arrayListOf<String>("A","B","C","D","E")
        // 設定右上角的 menu
        toolbar = findViewById(R.id.edittype_toolbar)

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //載入類別資料到ListView
        setTypeList(typeListData)

        //ItemClick
        typeList.setOnItemClickListener{parent, view, position, id ->
            val element = typeList.adapter.getItem(position) // The item that was clicked
            EditTypeDialog(element.toString()).show()
        }

    }
    //返回鍵 AND 右上收入支出的切換
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.costtype -> Toast.makeText(this, "支出", Toast.LENGTH_SHORT).show()
            R.id.incometype -> Toast.makeText(this, "收入", Toast.LENGTH_SHORT).show()
            else -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
    //創造menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.typemenu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    //載入類別紀錄資料
    private fun setTypeList(ListData: ArrayList<String>){
        var adapter = EditTypeListAdapter(this,ListData)
        typeList.adapter = adapter

    }
    //對話方塊
    private fun EditTypeDialog(typeName: String): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("編輯類別")
        //設定對話方塊內部元件 類似在OnCreate()裡面
        val inflater = LayoutInflater.from(application)
        val view: View = inflater.inflate(R.layout.edittype_dialog, null)
        builder.setView(view)
        val editTypeName = view.findViewById<EditText>(R.id.editTypeName)

        editTypeName.setText(typeName)


        builder.setPositiveButton("編輯") { _, _ ->
            Toast.makeText(this, "我按了確認", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("刪除") { _, _ ->
            Toast.makeText(this, "我按了刪除", Toast.LENGTH_SHORT).show()
        }
        val dialog = builder.create()


        return dialog
    }
}


