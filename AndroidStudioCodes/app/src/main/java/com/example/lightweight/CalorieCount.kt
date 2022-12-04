package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate

class CalorieCount : AppCompatActivity(), AdapterClass.ClickListener {
    var ref: DatabaseReference? = null /**/
    var recView : RecyclerView? = null
    var takenCal = 0

    // TUÇEMİ SEVİORUM

    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)

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

        /*********************************************************/

        ref = userReference?.child("besin")?.child("besin kayıtları")?.child(LocalDate.now().toString())
        recView = findViewById(R.id.foods)

        /*takenCal = 0
        var list: ArrayList<meal>? = ArrayList<meal>()
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (ds in snapshot.children)
                        {
                            /*val temp: ArrayList<String> = ArrayList<String>()
                            for (v in ds.children) {
                                temp.add(v.value.toString())
                            }
                            val m = meal(ds.key, temp)*/
                            list?.add(ds.getValue(meal::class.java)!!)
                            takenCal += list?.last()?.kalori!!.toInt()
                        }
                        val adapterC: AdapterClass = AdapterClass(list!!,this@CalorieCount)
                        recView?.adapter = adapterC
                    }
                    calculations(takenCal)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CalorieCount, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }*/

        val newFood:  Button = findViewById(R.id.newFood)
        newFood.setOnClickListener() {
            val intent = Intent(this, EnterFood::class.java)
            startActivity(intent)
        }

        /******************** Menu ***************************************/
        val shp:  ImageButton = findViewById(R.id.shopage)

        shp.setOnClickListener() {
            val intent = Intent(this, Shop::class.java)
            startActivity(intent)
            finish()
        }

        val profilePageButton:  ImageButton = findViewById(R.id.userpage)

        profilePageButton.setOnClickListener() {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }

        val food:  ImageButton = findViewById(R.id.foodpage)
        food.isClickable = false

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        homeButton.setOnClickListener {
            val intent = Intent(this, homePage::class.java)
            startActivity(intent)
            finish()
        }
        val gymButton = findViewById<ImageButton>(R.id.gympage)
        gymButton.setOnClickListener {

            val intent = Intent (this, Workout::class.java)
            startActivity(intent)
            finish()

        }

        /**************************************************/

    }

    override fun onStart() {
        super.onStart()
        takenCal = 0
        var list: ArrayList<meal>? = ArrayList<meal>()
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (ds in snapshot.children)
                        {
                            /*val temp: ArrayList<String> = ArrayList<String>()
                            for (v in ds.children) {
                                temp.add(v.value.toString())
                            }
                            val m = meal(ds.key, temp)
                            list?.add(m)
                            takenCal += m.kalori!!.toInt()*/
                            list?.add(ds.getValue(meal::class.java)!!)
                            takenCal += list?.last()?.kalori!!.toInt()
                        }
                        val adapterC: AdapterClass = AdapterClass(list!!,this@CalorieCount)
                        recView?.adapter = adapterC
                    }
                    calculations(takenCal)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CalorieCount, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

   /* override fun onStop() {
        super.onStop()

        /*********************************************************/
        val newFood:  Button = findViewById(R.id.newFood)
        newFood.setOnClickListener() {
            val intent = Intent(this, EnterFood::class.java)
            startActivity(intent)
        }
        ref = userReference?.child("besin")?.child("besin kayıtları")?.child(LocalDate.now().toString())
        recView = findViewById(R.id.foods)
        var list: ArrayList<meal>? = ArrayList<meal>()
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (ds in snapshot.children)
                        {
                            /*val temp: ArrayList<String> = ArrayList<String>()
                            for (v in ds.children) {
                                temp.add(v.value.toString())
                            }
                            val m = meal(ds.key, temp)
                            list?.add(m)
                            takenCal += m.kalori!!.toInt()*/
                            list?.add(ds.getValue(meal::class.java)!!)
                            takenCal += list?.last()?.kalori!!.toInt()
                        }
                        val adapterC: AdapterClass = AdapterClass(list!!,this@CalorieCount)
                        recView?.adapter = adapterC
                    }
                    calculations(takenCal)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CalorieCount, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }*/


    fun calculations(takenCal: Int){
        var c_bar: ProgressBar = findViewById (R.id.caloryProgressBar)
        var tc_text: TextView= findViewById (R.id.takenCalVal)
        var ttc_text:TextView = findViewById (R.id.totalCalVal)
        var rc_text: TextView = findViewById (R.id.remainCalVal)
        var cPercent: TextView = findViewById (R.id.c_persent)

        var totalCal = ttc_text?.text.toString().toIntOrNull() ?: 0 //TODO with firebase

        var remain = totalCal - takenCal
        if(c_bar?.progress!! < 100) c_bar?.progress = (takenCal*100)/totalCal
        val percent = (takenCal*100)/totalCal
        cPercent?.text = "%$percent"
        tc_text?.text = "$takenCal"
        remain = totalCal - takenCal
        rc_text?.text = "$remain"

    }


    override fun ClickedItem(meal: meal) {

    }
}