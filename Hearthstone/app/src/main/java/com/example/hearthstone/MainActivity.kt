package com.example.hearthstone

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ourApplication = this.application
    }
    companion object {
        lateinit var ourApplication: Application
    }
}
