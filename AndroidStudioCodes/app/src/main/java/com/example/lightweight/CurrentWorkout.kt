package com.example.lightweight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.math.log10
import com.example.lightweight.databinding.ActivityCurrentWorkoutBinding



class CurrentWorkout : AppCompatActivity() /*, AdaptoWList.ClickListener*/ {

    lateinit var binding: ActivityCurrentWorkoutBinding
    private lateinit var auth: FirebaseAuth /**/
    var databaseReference: DatabaseReference? = null /**/
    var database: FirebaseDatabase? = null /**/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_current_workout) /*this might be important*/
        val binding = ActivityCurrentWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() /**/
        database = FirebaseDatabase.getInstance() /**/
        databaseReference = database?.reference!!.child("Kullanıcılar")
        var currentUser = auth.currentUser

        //Kullanıcının id'sini alıyoruz
        var userReference = databaseReference?.child(currentUser?.uid!!)

        val actionBar = supportActionBar
        actionBar!!.hide()
        var clickCount = 0
        var tv = findViewById<TextView>(R.id.clickcounttext)
        var hareket = findViewById<TextView>(R.id.workoutNamea)
        //userReference?.child("workout plans")?.child("plan1")?.child(WorkoutPlanList.workoutid.toString())?.setValue(WorkoutPlanList)
        val nextMoveButton1: Button = findViewById(R.id.nextMoveB)
        nextMoveButton1.setOnClickListener() {

            tv.setText("Su ana kadar yapilan hareket: $clickCount  ")
            //hareket.setText("")
            //programlist[clickCount-1].inside?.size!!
            userReference?.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.workoutNamea.text = snapshot.child("workout plans").child("plan1").child((clickCount-1).toString()).child("id").value.toString()
                    binding.workoutagirlika.text = snapshot.child("workout plans").child("plan1").child((clickCount-1).toString()).child("ağırlık").value.toString()
                    binding.workoutseta.text = snapshot.child("workout plans").child("plan1").child((clickCount-1).toString()).child("set").value.toString()
                    binding.workouttekrara.text = snapshot.child("workout plans").child("plan1").child((clickCount-1).toString()).child("tekrar").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



            //binding.workoutNamea.text = snapshot.child
            clickCount++
        }









        val homeButton = findViewById<ImageButton>(R.id.homepage)
        homeButton.setOnClickListener() {
            val intent = Intent(this, homePage::class.java)
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

        val prfl:  ImageButton = findViewById(R.id.userpage)

        prfl.setOnClickListener() {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        val gympg:  ImageButton = findViewById(R.id.gympage)

        gympg.setOnClickListener() {
            val intent = Intent(this, Workout::class.java)
            startActivity(intent)
        }


    }


    /*override fun ClickedItem(workoutPlanList: workoutPlanList) {

    }*/

}