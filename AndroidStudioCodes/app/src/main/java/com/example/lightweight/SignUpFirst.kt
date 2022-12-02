package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SignUpFirst : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_first)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val nextButtonOnPage1 = findViewById<Button>(R.id.nextSignIn)
        val nameofUser = findViewById<EditText>(R.id.nameInfoFromUser)
        var enteredName = ""

        //im hereeeeeeee

        nextButtonOnPage1.setOnClickListener {

            enteredName = nameofUser.text.toString()
            val intent = Intent(this, SignUpSecond::class.java)
            intent.putExtra("USER", enteredName)
            startActivity(intent)
        }

    }
}