package com.example.msgshareapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LifeCycleActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
    }
}