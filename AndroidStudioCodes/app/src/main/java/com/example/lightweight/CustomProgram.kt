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
import android.widget.*

class CustomProgram : AppCompatActivity() , AdapterCustom.ClickListener{

    var ref: DatabaseReference? = null /**/

    //var list: ArrayList<workoutPlanList>? = ArrayList<workoutPlanList>()
    var recView: RecyclerView? = null
    private var sView: SearchView? = null
    var list: ArrayList<workoutplan>? = ArrayList<workoutplan>()
    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)
    var FullList = ArrayList<workoutplan>()

    var control = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_program)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val prevPage: ImageButton = findViewById(R.id.prevPage2)

        prevPage.setOnClickListener() {
            finish()
        }
        val wkayit: Button = findViewById(R.id.enterWorkout)
        wkayit.setOnClickListener() {
            userReference?.child("workout plans")?.setValue("custom workout")
            userReference?.child("workout plans")?.child("custom workout")?.setValue(FullList)
            finish()
        }
        /****************************************************************/
        //refs
        ref = FirebaseDatabase.getInstance().reference.child("WorkoutMoves")
        recView = findViewById(R.id.customWList)
        sView = findViewById(R.id.workoutSearch)

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

            val intent = Intent(this, Workout::class.java)
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
        control = 0

        if (ref != null) {
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //userReference?.child("workout plans")?.child("plan1")?.child(Workoutplan.workoutid.toString())?.child("workoutid")?.removeValue()
                    if (snapshot.exists()) {
                        for (ds in snapshot.children) {
                            val temp: ArrayList<String> = ArrayList<String>()
                            for (v in ds.children) {
                                temp.add(v.value.toString())
                            }
                            val m = workoutplan(ds.key, temp)
                            list?.add(m)
                        }
                        var adapterC: AdapterCustom = AdapterCustom(list!!, this@CustomProgram)
                        recView?.adapter = adapterC
                        /*change program or workout*/

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CustomProgram, error.message, Toast.LENGTH_SHORT).show()
                }
            })
            //control = 1
        }

        if (sView != null) {
            sView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    search(p0)
                    return true
                }

            })
        }
    }

    fun search(str: String?) {
        val mylist2: ArrayList<workoutplan> = ArrayList<workoutplan>()
        for (obj: workoutplan in list!!) {
            if (obj.id?.lowercase()!!.contains(str!!.lowercase())) {
                mylist2.add(obj)
            }
        }
        var adapterC: AdapterCustom = AdapterCustom(mylist2, this@CustomProgram)
        recView?.adapter = adapterC

    }

    override fun ClickedItem(workoutplan : workoutplan) {
        var checking = findViewById<TextView>(R.id.check)
        checking.setText(workoutplan.id.toString() + " Eklendi")
        FullList.add(workoutplan)
        Thread.sleep(50)
        Thread.sleep(50)
        onStart()

    }
}