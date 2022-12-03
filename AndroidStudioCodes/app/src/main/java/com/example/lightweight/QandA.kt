package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import android.net.Uri;

class QandA : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qa)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        val gymButton = findViewById<ImageButton>(R.id.gympage)
        val profileButton = findViewById<ImageButton>(R.id.userpage)

        profileButton.isClickable = false
        val question5: Button = findViewById (R.id.question5)

        question5.setOnClickListener() {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://fitbod.me/blog/not-sore-after-workout/"))
                startActivity(i)
        }

        val question1: Button = findViewById (R.id.question1)

        question1.setOnClickListener() {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.healthline.com/health/fitness/rest-between-sets#muscle-fuel"))
            startActivity(i)
        }

        val question2: Button = findViewById (R.id.question2)

        question2.setOnClickListener() {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.healthline.com/health/exercise-fitness/what-to-do-after-working-out"))
            startActivity(i)
        }

        val question3: Button = findViewById (R.id.question3)

        question3.setOnClickListener() {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://health.osu.edu/wellness/exercise-and-nutrition/micronutrients-vs-macronutrients"))
            startActivity(i)
        }

        val question4: Button = findViewById (R.id.question4)

        question4.setOnClickListener() {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.healthline.com/health/exercise-fitness/rest-day"))
            startActivity(i)
        }

        val question6: Button = findViewById (R.id.question6)

        question6.setOnClickListener() {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.healthline.com/health/fitness-exercise/best-time-to-workout"))
            startActivity(i)
        }

        val question7: Button = findViewById (R.id.question7)

        question7.setOnClickListener() {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.webmd.com/fitness-exercise/features/sore-muscles-after-workout"))
            startActivity(i)
        }

        val question8: Button = findViewById (R.id.question8)

        question8.setOnClickListener() {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.healthline.com/nutrition/how-much-time-between-eating-and-working-out#1"))
            startActivity(i)
        }

        val question9: Button = findViewById (R.id.question9)

        question9.setOnClickListener() {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://fitbod.me/blog/best-way-to-track-workouts/"))
            startActivity(i)
        }

        val question10: Button = findViewById (R.id.question10)

        question10.setOnClickListener() {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.webmd.com/fitness-exercise/features/how-to-stretch"))
            startActivity(i)
        }


        homeButton.setOnClickListener {

            val intent = Intent(this, homePage::class.java)
            startActivity(intent)

        }

        gymButton.setOnClickListener {


            val intent = Intent(this, Workout::class.java)
            startActivity(intent)

        }



        val shp:  ImageButton = findViewById(R.id.shopage)

        shp.setOnClickListener() {
            val intent = Intent(this, Shop::class.java)
            startActivity(intent)
        }

        val food:  ImageButton = findViewById(R.id.foodpage)

        food.setOnClickListener() {
            val intent = Intent(this, CalorieCount::class.java)
            startActivity(intent)
        }

        val profilePageButton:  ImageButton = findViewById(R.id.userpage)

        profilePageButton.setOnClickListener() {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

    }

}