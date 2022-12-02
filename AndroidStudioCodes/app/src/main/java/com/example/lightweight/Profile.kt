package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val editProfileButton = findViewById<Button>(R.id.editProfile)
        val dietTrackerButton = findViewById<Button>(R.id.dietTracker)
        val workoutActivitiesButton = findViewById<Button>(R.id.workoutActivities)
        val qaButton = findViewById<Button>(R.id.QandA)
        val logOutButton = findViewById<Button>(R.id.logOut)

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        val gymButton = findViewById<ImageButton>(R.id.gympage)
        val foodButton = findViewById<ImageButton>(R.id.foodpage)
        val shopButton = findViewById<ImageButton>(R.id.shopage)
        val profileButton = findViewById<ImageButton>(R.id.userpage)

        profileButton.isClickable = false

        editProfileButton.setOnClickListener {
            editProfileButton.error = "Edit is not available yet!"
            /* Toast.makeText(
                this@Profile,
                "Edit is not available yet!"
                Toast.LENGTH_SHORT
            ).show() */
        }
        dietTrackerButton.setOnClickListener {
            // food history
        }

        workoutActivitiesButton.setOnClickListener {
            // history page must be implemented
        }

        qaButton.setOnClickListener {
            /*
            wait for ilayda
            val intent = Intent (this, ::class.java)
            startActivity(intent)
            */
        }
        logOutButton.setOnClickListener {

            // firebase stuff
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {

            val intent = Intent(this, homePage::class.java)
            startActivity(intent)

        }

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

    }
}