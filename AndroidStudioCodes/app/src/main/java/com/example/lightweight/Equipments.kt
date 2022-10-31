package com.example.lightweight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Equipments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipments)

        val actionBar = supportActionBar
        actionBar!!.hide()
    }
}