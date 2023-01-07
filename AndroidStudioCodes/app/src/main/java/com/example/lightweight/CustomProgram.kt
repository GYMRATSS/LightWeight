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
import java.time.LocalDate

class CustomProgram : AppCompatActivity() , AdaptoWList.ClickListener {

    var ref: DatabaseReference? = null /**/
    var list: ArrayList<workoutPlanList>? = ArrayList<workoutPlanList>()
    var recView : RecyclerView? = null
    private var sView : SearchView? = null

    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_program)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val prevPage: ImageButton = findViewById(R.id.prevPage2)

        prevPage.setOnClickListener() {
            finish()
        }
        /****************************************************************/
    //refs

        /******************** Menu ***************************************/

        val shp: ImageButton = findViewById(R.id.shopage)

        shp.setOnClickListener() {
            val intent = Intent(this, Shop::class.java)
            startActivity(intent)
        }

        val food: ImageButton = findViewById(R.id.foodpage)

        food.setOnClickListener() {
            val intent = Intent(this, CalorieCount::class.java)
            startActivity(intent)
        }

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        val gymButton = findViewById<ImageButton>(R.id.gympage)
        val profileButton = findViewById<ImageButton>(R.id.userpage)

        profileButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        gymButton.setOnClickListener {

            val intent = Intent (this, Workout::class.java)
            startActivity(intent)

        }

        homeButton.setOnClickListener {
            val intent = Intent(this, homePage::class.java)
            startActivity(intent)
        }


    /***********************************************************************************/


    }

    override fun onStart() {
        super.onStart()
    }

    override fun ClickedItem(workoutPlanList: workoutPlanList) {
        TODO("Not yet implemented")
    }
}