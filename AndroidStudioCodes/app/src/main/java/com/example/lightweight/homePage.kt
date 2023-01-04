package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.example.lightweight.databinding.ActivityHomePageBinding
import com.example.lightweight.databinding.ActivityUpdateInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

class homePage : AppCompatActivity() {

    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.hide()

        userReference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.hiName.setText("Merhaba " + snapshot.child("İsim-soyisim").value.toString())
                binding.totalCalVal.setText(snapshot.child("Kalori ihtiyacı").value.toString())
                var a = snapshot.child("besin").child("kalori kayıtları").child(
                    LocalDate.now().toString()).child("Kalan kalori").value.toString()
                if(a=="null"){
                    binding.remainCalVal.setText(binding.totalCalVal.text.toString())
                    binding.takenCalVal.setText("0")
                }else {
                binding.remainCalVal.setText(snapshot.child("besin").child("kalori kayıtları").child(
                    LocalDate.now().toString()).child("Kalan kalori").value.toString())
                binding.takenCalVal.setText(snapshot.child("besin").child("kalori kayıtları").child(
                    LocalDate.now().toString()).child("Alınan kalori").value.toString()) }

                val c_bar: ProgressBar = findViewById (R.id.caloryProgressBar)
                val tc_text: TextView = findViewById (R.id.takenCalVal)
                val ttc_text: TextView = findViewById (R.id.totalCalVal)
                val cPercent: TextView = findViewById (R.id.c_persent)

                var takenCal = tc_text.text.toString().toIntOrNull() ?: 0 //TODO with firebase
                var totalCal = ttc_text.text.toString().toIntOrNull() ?: 0 //TODO with firebase
                c_bar.progress = (takenCal*100)/totalCal
                val percent = (takenCal*100)/totalCal
                cPercent.text = "%$percent"

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //val userName = intent.getStringExtra("USER")
        //val textView = findViewById<TextView>(R.id.hiName)
        //val message = "Merhaba, $userName"          // name database'den gelsin suan sadece sign uptan geleni yazıyor
        //textView.text = message

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        val gymButton = findViewById<ImageButton>(R.id.gympage)
        val foodButton = findViewById<ImageButton>(R.id.foodpage)
        val shopButton = findViewById<ImageButton>(R.id.shopage)
        val profileButton = findViewById<ImageButton>(R.id.userpage)

        homeButton.isClickable = false



        val activityBar = findViewById<ProgressBar>(R.id.workoutProgressBar)
        activityBar.progress = 0                                               // hardcoded progress change it!!!
        val workoutPercent = findViewById<TextView>(R.id.workout_percent)
        workoutPercent.text = "%0"


        /***********************Menu*******************/

        gymButton.setOnClickListener {

            val intent = Intent (this, Workout::class.java)
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
}