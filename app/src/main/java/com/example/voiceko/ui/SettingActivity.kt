package com.example.voiceko.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.voiceko.Controller.EnterDataController
import com.example.voiceko.R


class SettingActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var gofixedbtn: ImageView
    private lateinit var gosetbudgetbtn: ImageView
    private lateinit var goedittypebtn: ImageView
    private lateinit var controller:EnterDataController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        controller = EnterDataController.instance
        controller.init(this)
        toolbar = findViewById(R.id.setting_toolbar)

        //工具列，設置返回鍵啟用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        gofixedbtn = findViewById(R.id.setting_fixedbtn)
        gosetbudgetbtn = findViewById(R.id.setting_setbudgetbtn)
        goedittypebtn = findViewById(R.id.setting_edittypebtn)
        gofixedbtn.setImageResource(R.drawable.fixed_money)
        gosetbudgetbtn.setImageResource(R.drawable.budget_set)
        goedittypebtn.setImageResource(R.drawable.edit_cate)


        gofixedbtn.setOnClickListener {goFixedCost()}
        gosetbudgetbtn.setOnClickListener {goSetBudget()}
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
    private fun goSetBudget(){
        val intent = Intent(this, SetBudgetActivity::class.java)
        startActivity(intent)
    }
    private fun goFixedCost(){
        val intent = Intent(this, EditFixedCostActivity::class.java)
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