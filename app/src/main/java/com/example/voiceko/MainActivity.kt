package com.example.voiceko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn1:Button = findViewById<Button>(R.id.button)
        btn1.setOnClickListener(ttt)

    }
    var ttt = View.OnClickListener {
        Toast.makeText(this,"fuck",Toast.LENGTH_LONG).show()
    }

}
