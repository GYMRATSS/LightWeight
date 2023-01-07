package com.example.lightweight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.math.log10
import com.example.lightweight.databinding.ActivityCurrentWorkoutBinding
import com.example.lightweight.databinding.ActivityUpdateInfoBinding


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
        //var clickCount = 0
        //Size_programlist.mySizePL
        var tv = findViewById<TextView>(R.id.clickcounttext)
        var tvagirlik = findViewById<TextView>(R.id.textagirlik)
        var tvset = findViewById<TextView>(R.id.textset)
        var tvtekrar = findViewById<TextView>(R.id.texttekrar)
        //var tvhareket = findViewById<TextView>(R.id.workoutNamea)
        var tvhazirlan = findViewById<TextView>(R.id.hazirlan)

        val nextMoveButton1: Button = findViewById(R.id.nextMoveB)

        val imageView: ImageView = findViewById(R.id.theImageView)
        var imageId = ""

        if (((Size_programlist.clickCount+1) <= Size_programlist.mySizePL) ) {
            nextMoveButton1.setOnClickListener() {

                if(Size_programlist.clickCount+1 <= Size_programlist.mySizePL) {
                    Size_programlist.clickCount++
                }
                /*else{
                    Thread.sleep(300)
                    val intent = Intent(this, Workout::class.java)
                    startActivity(intent)
                }*/
                //Size_programlist.clickCount++

                tv.setText("Su ana kadar yapilan hareket: ${Size_programlist.clickCount}  ")

                userReference?.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        //imageId = resources.getIdentifier("plan" + snapshot.child("workout plans").children.first().value.toString() + "_" + (Size_programlist.clickCount + 1), "drawable", packageName)
                        //imageId = resources.getIdentifier("image" + (Size_programlist.clickCount + 1), "drawable", packageName)
                        if((Size_programlist.clickCount != 0) && ((Size_programlist.clickCount) <= Size_programlist.mySizePL)) {
                            tvset.setText("Set: ")
                            tvagirlik.setText("Ağırlık: ")
                            tvtekrar.setText("Tekrar: ")
                            tvhazirlan.setText("")

                            imageId = snapshot.child("workout plans").children.first()
                                .child((Size_programlist.clickCount ).toString()).child("id").value.toString().lowercase().replace(" ", "")
                            binding.workoutNamea.text = snapshot.child("workout plans").children.first()
                                .child((Size_programlist.clickCount - 1).toString()).child("id").value.toString()
                            binding.workoutagirlika.text =
                                snapshot.child("workout plans").children.first()
                                    .child((Size_programlist.clickCount - 1).toString())
                                    .child("ağırlık").value.toString()
                            binding.workoutseta.text = snapshot.child("workout plans").children.first()
                                .child((Size_programlist.clickCount - 1).toString()).child("set").value.toString()
                            binding.workouttekrara.text = snapshot.child("workout plans").children.first()
                                .child((Size_programlist.clickCount - 1).toString()).child("tekrar").value.toString()

                            //imageId = binding.workoutNamea.toString().lowercase().replace(" ", "")

                        }

                        //
                    }


                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

                var imageName = resources.getIdentifier(imageId,  "drawable", packageName)
                imageView.setImageResource(imageName)


            }
            /*if(clickCount+1 > Size_programlist.mySizePL){
                finish()
            }*/
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