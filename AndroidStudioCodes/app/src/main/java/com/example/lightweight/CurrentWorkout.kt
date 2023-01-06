package com.example.lightweight

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class CurrentWorkout : AppCompatActivity(), AdaptoWList.ClickListener {

    var ref: DatabaseReference? = null /**/

    var recView : RecyclerView? = null

    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_workout)

        val actionBar = supportActionBar
        actionBar!!.hide()

        //
       /* ref = userReference?.child("workout plans")?.child("plan1")

        var list: ArrayList<workoutPlanList>? = ArrayList<workoutPlanList>()

        var i = 0
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for(ds in snapshot.children)
                        {
                            /*val temp: ArrayList<String> = ArrayList<String>()
                            for (v in ds.children) {
                                temp.add(v.value.toString())
                            }
                            val m = workoutplan(ds.key, temp)*/
                            /*list?.add(m)*/

                            list?.add(ds.getValue(workoutPlanList::class.java)!!)


                        }

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CurrentWorkout, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }*/
        var clickCount = 0
        //userReference?.child("workout plans")?.child("plan1")?.child(WorkoutPlanList.workoutid.toString())?.setValue(WorkoutPlanList)
        val nextMoveButton1 = findViewById<ImageButton>(R.id.nextMoveB)
        nextMoveButton1.setOnClickListener {
            clickCount++
        //kullanici bilgisi-> 0 1 2 (clickcount) -> agirlik, set vs
        }
    /************************menu****************************/
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

        /*************************************************************/
    }


    override fun ClickedItem(workoutPlanList: workoutPlanList) {

    }

}