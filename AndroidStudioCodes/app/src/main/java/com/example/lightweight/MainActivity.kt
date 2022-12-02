package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val signIn: Button = findViewById (R.id.signIn)

        signIn.setOnClickListener() {
            val intent = Intent(this, CalorieCount::class.java) //TODO
            startActivity(intent)
        }

        val logIn: Button = findViewById (R.id.logIn)
        val eMail: EditText  = findViewById(R.id.editTextTextEmailAddress)
        val pass: EditText  = findViewById(R.id.editTextTextPassword)


        eMail.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                    pass.addTextChangedListener(object: TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }
                        override fun afterTextChanged(p0: Editable?) {
                            if(pass.text.toString() == "admin" && eMail.text.toString() == "admin"){
                                logIn.isEnabled = true
                                logIn.setError(null)
                            }else{
                                logIn.isEnabled = false
                                logIn.error = "Incorrect password or e-mail"
                            }
                        }
                    })
            }

        })
        logIn.setOnClickListener() {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }

        val tempSignUpButton = findViewById<Button>(R.id.tempSignUp)

        tempSignUpButton.setOnClickListener {
            val intent = Intent(this, SignUpFirst::class.java)
            startActivity(intent)
        }

    }
}