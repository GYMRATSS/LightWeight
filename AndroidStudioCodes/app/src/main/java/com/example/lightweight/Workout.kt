package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate


class Workout : AppCompatActivity(), AdapterClassWorkout.ClickListener {

    var ref: DatabaseReference? = null /**/
    var list: ArrayList<workoutplan>? = ArrayList<workoutplan>()
    var recView : RecyclerView? = null

   /* private lateinit var auth: FirebaseAuth /**/
    var databaseReference: DatabaseReference? = null /**/
    var database: FirebaseDatabase? = null /**/*/

    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val gymButton = findViewById<ImageButton>(R.id.gympage)

        gymButton.isClickable = false

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        homeButton.setOnClickListener {
            val intent = Intent(this, homePage::class.java)
            startActivity(intent)
        }

        val shp:  ImageButton = findViewById(R.id.shopage)



       /* var currentUser = auth.currentUser
        //FirebaseAuth.getInstance().currentUser?.uid.toString()
        //Kullanıcının id'sini alıyoruz
        var userReference = databaseReference?.child(currentUser?.uid!!)
        ref = userReference?.child("workout plans")?.child("plan1")*/
            /*.child(LocalDate.now().toString())   cikarildi*/
        ref = userReference?.child("workout plans")?.child("plan1")
        recView = findViewById(R.id.moves)


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
                            val m = workoutplan(ds.key, temp)*/
                            /*list?.add(m)*/
                            list?.add(ds.getValue(workoutplan::class.java)!!)

                        }
                        val adapterC: AdapterClassWorkout = AdapterClassWorkout(list!!,this@Workout)
                        recView?.adapter = adapterC
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Workout, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }




        shp.setOnClickListener() {
            val intent = Intent(this, Shop::class.java)
            startActivity(intent)
        }

        val startwo:  Button = findViewById(R.id.startbutton)

        startwo.setOnClickListener() {
            val intent = Intent(this, CurrentWorkout::class.java)
            startActivity(intent)
        }

        val changep:  Button = findViewById(R.id.changeprogrambutton)

        changep.setOnClickListener() {
            val intent = Intent(this, ChangeProgram::class.java)
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





    }
    override fun onResume() {
        super.onResume()
        ref = userReference?.child("workout plans")?.child("plan1")?.child(LocalDate.now().toString())
        recView = findViewById(R.id.moves)
        var list: ArrayList<workoutplan>? = ArrayList<workoutplan>()

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
                            val m = workoutplan(ds.key, temp)
                            list?.add(m)*/
                            list?.add(ds.getValue(workoutplan::class.java)!!)
                        }
                        val adapterC: AdapterClassWorkout = AdapterClassWorkout(list!!,this@Workout)
                        recView?.adapter = adapterC
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    /*Toast.makeText(this@Workout, error.message, Toast.LENGTH_SHORT).show()*/
                }
            })
        }
    }


    override fun ClickedItem(workoutplan : workoutplan) {

    }

}