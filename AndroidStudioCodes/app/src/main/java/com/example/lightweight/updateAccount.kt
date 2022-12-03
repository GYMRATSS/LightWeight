package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class updateAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_account)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val closeButton = findViewById<ImageButton>(R.id.closeUpdateAccount)

        closeButton.setOnClickListener {
            val intent = Intent (this, Profile::class.java)
            startActivity(intent)
        }

    }
}