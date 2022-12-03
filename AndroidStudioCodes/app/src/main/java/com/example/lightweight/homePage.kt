package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView

class homePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val userName = intent.getStringExtra("USER")
        val textView = findViewById<TextView>(R.id.hiName)
        val message = "Merhaba, $userName"          // name database'den gelsin suan sadece sign uptan geleni yazıyor
        textView.text = message

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        val gymButton = findViewById<ImageButton>(R.id.gympage)
        val foodButton = findViewById<ImageButton>(R.id.foodpage)
        val shopButton = findViewById<ImageButton>(R.id.shopage)
        val profileButton = findViewById<ImageButton>(R.id.userpage)

        homeButton.isClickable = false

        val c_bar: ProgressBar = findViewById (R.id.caloryProgressBar)
        val tc_text: TextView = findViewById (R.id.takenCalVal)
        val ttc_text: TextView = findViewById (R.id.totalCalVal)
        val cPercent: TextView = findViewById (R.id.c_persent)

        var takenCal = tc_text.text.toString().toIntOrNull() ?: 0 //TODO with firebase
        var totalCal = ttc_text.text.toString().toIntOrNull() ?: 0 //TODO with firebase
        c_bar.progress = (takenCal*100)/totalCal
        val percent = (takenCal*100)/totalCal
        cPercent.text = "%$percent"

        val activityBar = findViewById<ProgressBar>(R.id.workoutProgressBar)
        activityBar.progress = 30                                               // hardcoded progress change it!!!
        val workoutPercent = findViewById<TextView>(R.id.workout_percent)
        workoutPercent.text = "%30"


        /***********************Menu*******************/

        gymButton.setOnClickListener {
            /*
            wait for ilayda
            val intent = Intent (this, ::class.java)
            startActivity(intent)
            */
        }

        foodButton.setOnClickListener {

            val intent = Intent(this, CalorieCount::class.java)
            startActivity(intent)

        }

        shopButton.setOnClickListener {
            val intent = Intent(this, Shop::class.java)
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

    }
}