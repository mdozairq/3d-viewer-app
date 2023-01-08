package com.example.msgshareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class SecondActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val bundle : Bundle? = intent.extras
        val msg : String = bundle!!.get("user_msg") as String
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        val newMsg = findViewById<TextView>(R.id.showMsg)
        newMsg.text = msg
    }
}