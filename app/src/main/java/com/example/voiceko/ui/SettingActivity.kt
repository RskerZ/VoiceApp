package com.example.voiceko.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.R


class SettingActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var gofixedbtn: Button
    private lateinit var goaddtypebtn: Button
    private lateinit var goedittypebtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar = findViewById(R.id.setting_toolbar)

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        gofixedbtn = findViewById(R.id.setting_fixedbtn)
        goaddtypebtn = findViewById(R.id.setting_addtypebtn)
        goedittypebtn = findViewById(R.id.setting_edittypebtn)
        gofixedbtn.setOnClickListener {goFixedCost()}
        goaddtypebtn.setOnClickListener {addTypeDialog().show()}
        goedittypebtn.setOnClickListener {goEditType()}
    }
    //返回鍵
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    //前往各頁面
    private fun goAddType(){
        val intent = Intent(this, AddTypeActivity::class.java)
        startActivity(intent)
    }
    private fun goFixedCost(){
        val intent = Intent(this, FixCostActivity::class.java)
        startActivity(intent)
    }
    private fun goEditType(){
        val intent = Intent(this, EditTypeActivity::class.java)
        startActivity(intent)
    }
    //新增對話方塊
    private fun addTypeDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("新增類別")
        builder.setView(R.layout.addtype_dialog)
        builder.setPositiveButton("新增") { dialog, id ->
            Toast.makeText(this, "我按了新增", Toast.LENGTH_SHORT).show()
        }
        val dialog = builder.create()
        return dialog
    }

}