package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val signIn: Button = findViewById (R.id.signIn)

        signIn.setOnClickListener() {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }
}