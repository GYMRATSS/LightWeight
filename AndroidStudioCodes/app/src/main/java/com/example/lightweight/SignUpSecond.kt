package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUpSecond : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_second)
        val actionBar = supportActionBar
        actionBar!!.hide()

        val userName = intent.getStringExtra("USER")
        val textView = findViewById<TextView>(R.id.startHeader)
        val message = "Başlayalım, $userName!"
        textView.text = message

        val skipButtonOnPage2 = findViewById<Button>(R.id.skipButton)

        skipButtonOnPage2.setOnClickListener {

            // direct to home page
            val enteredName = userName
            val intent = Intent(this, homePage::class.java)
            intent.putExtra("USER",enteredName)
            startActivity(intent)

        }

    }
}