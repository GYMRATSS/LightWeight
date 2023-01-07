package com.example.lightweight

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import androidx.core.graphics.toColor
import com.example.lightweight.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate

class homePage : AppCompatActivity() {

    var ref: DatabaseReference? = null
    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)
    var theListView: ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.hide()

        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.hiName.setText("Merhaba " + snapshot.child("İsim-soyisim").value.toString())
                binding.totalCalVal.setText(snapshot.child("Kalori ihtiyacı").value.toString())
                var a = snapshot.child("besin").child("kalori kayıtları").child(
                    LocalDate.now().toString()
                ).child("Kalan kalori").value.toString()
                if (a == "null") {
                    binding.remainCalVal.setText(binding.totalCalVal.text.toString())
                    binding.takenCalVal.setText("0")
                } else {
                    binding.remainCalVal.setText(
                        snapshot.child("besin").child("kalori kayıtları").child(
                            LocalDate.now().toString()
                        ).child("Kalan kalori").value.toString()
                    )
                    binding.takenCalVal.setText(
                        snapshot.child("besin").child("kalori kayıtları").child(
                            LocalDate.now().toString()
                        ).child("Alınan kalori").value.toString()
                    )
                }

                var b = snapshot.child("besin").child("makro kayıtları").child(
                    LocalDate.now().toString()
                ).child("Karbonhidrat").value.toString()
                if (b == "null") {
                    binding.carbVal.setText("000")
                } else {
                    binding.carbVal.setText(
                        snapshot.child("besin").child("makro kayıtları").child(
                            LocalDate.now().toString()
                        ).child("Karbonhidrat").value.toString() + " gr"
                    )
                }

                var c = snapshot.child("besin").child("makro kayıtları").child(
                    LocalDate.now().toString()
                ).child("Yağ").value.toString()
                if (c == "null") {
                    binding.fatVal.setText("000")
                } else {
                    binding.fatVal.setText(
                        snapshot.child("besin").child("makro kayıtları").child(
                            LocalDate.now().toString()
                        ).child("Yağ").value.toString() + " gr"
                    )
                }

                var d = snapshot.child("besin").child("makro kayıtları").child(
                    LocalDate.now().toString()
                ).child("Protein").value.toString()
                if (d == "null") {
                    binding.proteinVal.setText("000")
                } else {
                    binding.proteinVal.setText(
                        snapshot.child("besin").child("makro kayıtları").child(
                            LocalDate.now().toString()
                        ).child("Protein").value.toString() + " gr"
                    )
                }

                val c_bar: ProgressBar = findViewById(R.id.caloryProgressBar)
                val tc_text: TextView = findViewById(R.id.takenCalVal)
                val ttc_text: TextView = findViewById(R.id.totalCalVal)
                val cPercent: TextView = findViewById(R.id.c_persent)

                var takenCal = tc_text.text.toString().toIntOrNull() ?: 0 //TODO with firebase
                var totalCal = ttc_text.text.toString().toIntOrNull() ?: 0 //TODO with firebase
                c_bar.progress = (takenCal * 100) / totalCal
                val percent = (takenCal * 100) / totalCal
                cPercent.text = "%$percent"

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

//----------------------------------------------------------------------------------------------------------


//----------------------------------------------------------------------------------------------------------

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        val gymButton = findViewById<ImageButton>(R.id.gympage)
        val foodButton = findViewById<ImageButton>(R.id.foodpage)
        val shopButton = findViewById<ImageButton>(R.id.shopage)
        val profileButton = findViewById<ImageButton>(R.id.userpage)

        homeButton.isClickable = false
/*
        val activityBar = findViewById<ProgressBar>(R.id.workoutProgressBar)
        activityBar.progress =
            0                                               // hardcoded progress change it!!!
        val workoutPercent = findViewById<TextView>(R.id.workout_percent)
        workoutPercent.text = "%0"  */


        /***********************Menu*******************/

        gymButton.setOnClickListener {

            val intent = Intent(this, Workout::class.java)
            startActivity(intent)

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

override fun onResume() {
    super.onResume()

    theListView = findViewById(R.id.workoutListView)

    ref = userReference?.child("workout plans")
    theListView = findViewById(R.id.workoutListView)
    //val textColor = Color.parseColor("@color/white")
    //theListView.setTextColor(textColor)

    var list = mutableListOf<String>()

    if (ref != null) {
        ref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) //plan1,2,..
                    {
                        for (v in ds.children) {    //0,1,2..
                            val theWorkout = v.getValue(workoutplan::class.java)
                            if(theWorkout != null){
                                list.add(theWorkout.id!! +": " +theWorkout.set!! +" x " + theWorkout.tekrar!!)
                            }
                        }
                    }
                }
                val workoutListToArray = list.toTypedArray()
                //val listadp = ArrayAdapter(this@homePage, android.R.layout.simple_list_item_1, workoutListToArray)
                val listadp = ArrayAdapter(this@homePage, R.layout.row, workoutListToArray)
                theListView?.adapter = listadp

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@homePage, error.message, Toast.LENGTH_SHORT).show()
            }
        })


    }
    }
}