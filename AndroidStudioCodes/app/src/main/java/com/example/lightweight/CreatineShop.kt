package com.example.lightweight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CreatineShop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creatine_shop)

        val actionBar = supportActionBar
        actionBar!!.hide()
    }
}