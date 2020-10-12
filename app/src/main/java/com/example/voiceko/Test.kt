package com.example.voiceko

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class Test : AppCompatActivity() {
    private val testFragment = LilCaculater()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val bt1 = findViewById<Button>(R.id.button5)
        bt1.setOnClickListener {
            addFragment(testFragment)
        }


    }
    private fun addFragment(f: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout,f)
        transaction.commit()
    }
}