package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView

class CalorieCount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie_count)

        val actionBar = supportActionBar
        actionBar!!.hide()
        /******** water entry **********/
        var remainW = 2000

        val p_bar: ProgressBar = findViewById (R.id.waterProgressBar)
        val r_text: TextView = findViewById (R.id.remainWater_m)
        fun updateProgressBar(){
            p_bar.progress = ((2000-remainW)*100)/2000
            r_text.text = "$remainW ml"
        }

        val water: ImageButton = findViewById (R.id.glassButton)
        water.setOnClickListener() {
            if(remainW > 0){
                remainW -= 200
                updateProgressBar()
            }
        }
        /******************************/

        val c_bar: ProgressBar = findViewById (R.id.caloryProgressBar)
        val tc_text: TextView = findViewById (R.id.takenCalVal)
        val ttc_text: TextView = findViewById (R.id.totalCalVal)
        val rc_text: TextView = findViewById (R.id.remainCalVal)
        val cPercent: TextView = findViewById (R.id.c_persent)

        var takenCal = tc_text.text.toString().toIntOrNull() ?: 0 //TODO with firebase
        var totalCal = ttc_text.text.toString().toIntOrNull() ?: 0 //TODO with firebase
        var remain = totalCal

        fun updateCalBar(){
            if(c_bar.progress < 100) c_bar.progress = (takenCal*100)/totalCal
            val percent = (takenCal*100)/totalCal
            cPercent.text = "%$percent"
            tc_text.text = "$takenCal"
            remain = totalCal - takenCal
            rc_text.text = "$remain"
        }
        updateCalBar()
        val newFood:  Button = findViewById(R.id.newFood)
        /*newFood.setOnClickListener() {
            takenCal += 100 //TODO new food calory will be entered here
            updateCalBar()
        }*/

        newFood.setOnClickListener() {
            val intent = Intent(this, EnterFood::class.java)
            startActivity(intent)
        }


        /******************** Menu ***************************************/
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
    }
}