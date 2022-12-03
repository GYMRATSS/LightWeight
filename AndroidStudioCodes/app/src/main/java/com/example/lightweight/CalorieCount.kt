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

        val profilePageButton:  ImageButton = findViewById(R.id.userpage)

        profilePageButton.setOnClickListener() {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        val food:  ImageButton = findViewById(R.id.foodpage)
        food.isClickable = false

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        homeButton.setOnClickListener {
            val intent = Intent(this, homePage::class.java)
            startActivity(intent)
        }

        ref = FirebaseDatabase.getInstance().reference.child("yemekler") //TODO will changed with user meal list
        recView = findViewById(R.id.foods)
    }

    override fun onStart(){
        super.onStart()
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (ds in snapshot.children)
                        {
                            val temp: ArrayList<String> = ArrayList<String>()
                            for (v in ds.children) {
                                temp.add(v.value.toString())
                            }
                            val m = meal(ds.key, temp)
                            list?.add(m)
                        }
                        var adapterC: AdapterClass = AdapterClass(list!!,this@CalorieCount)
                        recView?.adapter = adapterC
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CalorieCount, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    override fun ClickedItem(meal: meal) {

        val gymButton = findViewById<ImageButton>(R.id.gympage)

        gymButton.setOnClickListener {
            val intent = Intent(this, Workout::class.java)
            startActivity(intent)

        }

        /* food.setOnClickListener() {
            val intent = Intent(this, CalorieCount::class.java)
            startActivity(intent)
        } */
    }
}