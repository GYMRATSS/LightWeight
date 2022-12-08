package com.example.lightweight

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.databinding.ActivityChangeProgramBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate

/*clicklistener check*/
class ChangeProgram : AppCompatActivity(), AdaptoWList.ClickListener{

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
        setContentView(R.layout.activity_change_program)

        val actionBar = supportActionBar
        actionBar!!.hide()

        /*try changing val name*/
        val prevPage: ImageButton = findViewById(R.id.prevPage)

        prevPage.setOnClickListener() {
            finish()
        }

        /****************************************************************/


        ref = FirebaseDatabase.getInstance().reference.child("workout plans")
        recView = findViewById(R.id.programList)
        sView = findViewById(R.id.programSearch)



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
    }

    /***********************************************************************************/

    override fun onStart(){
        super.onStart()
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (ds in snapshot.children)
                        {
                            val m = workoutPlanList(ds.key, ds.value.toString())
                            list?.add(m)
                        }
                        /*change program or workout*/
                        val adapterC: AdaptoWList = AdaptoWList(list!!,this@ChangeProgram)
                        recView?.adapter = adapterC
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChangeProgram, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        if (sView != null){
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

    fun search(str: String?){
        val mylist2: ArrayList<workoutPlanList> = ArrayList<workoutPlanList>()
        for (obj : workoutPlanList in list!!)
        {
            if(obj.workoutid?.lowercase()!!.contains(str!!.lowercase())){
                mylist2.add(obj)
            }
        }
        var adapterC: AdaptoWList = AdaptoWList(mylist2, this@ChangeProgram)
        recView?.adapter = adapterC

    }

    override fun ClickedItem(Workoutplan : workoutPlanList) {

        /*userReference?.child("workout plans")?.child("plan1")?.child(Workoutplan.workoutid.toString())?.child("ağırlık")?.setValue(Workoutplan)
        userReference?.child("workout plans")?.child("plan1")?.child(Workoutplan.workoutid.toString())?.child("set")?.setValue(Workoutplan)
        userReference?.child("workout plans")?.child("plan1")?.child(Workoutplan.workoutid.toString())?.child("tekrar")?.setValue(Workoutplan)
        finish()*/
        userReference?.child("workout plans")?.child("plan1")?.child(Workoutplan.workoutid.toString())?.setValue(Workoutplan)
        userReference?.child("workout plans")?.child("plan1")?.child(Workoutplan.workoutid.toString())?.child("workoutid")?.removeValue()
        /*finish()*/

    }
}