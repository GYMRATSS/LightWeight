package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView

class Shop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val protein: Button = findViewById (R.id.protein)

        protein.setOnClickListener() {

        }

        val creatine: Button = findViewById (R.id.creatine)

        creatine.setOnClickListener() {

        }

        val bcaa: Button = findViewById (R.id.bcaa)

        bcaa.setOnClickListener() {

        }

        val eq: Button = findViewById (R.id.gymEquipments)

        eq.setOnClickListener() {

        }

        /**********************************************/
        val shp:  ImageButton = findViewById(R.id.shopage)

        /* shp.setOnClickListener() {
            val intent = Intent(this, Shop::class.java)
            startActivity(intent)
        } */

        shp.isClickable = false

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

        val homeButton = findViewById<ImageButton>(R.id.homepage)

        homeButton.setOnClickListener {

            val intent = Intent(this, homePage::class.java)
            startActivity(intent)

        }
    }
}