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

        /*custom program butonu*/
        /*val customPageB: ImageButton = findViewById(R.id.customprogramb)

        customPageB.setOnClickListener() {
            val intent = Intent(this, CustomProgram::class.java)
            startActivity(intent)
        }*/

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
        var i = 0
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //userReference?.child("workout plans")?.child("plan1")?.child(Workoutplan.workoutid.toString())?.child("workoutid")?.removeValue()
                    if (snapshot.exists()){
                        for (ds in snapshot.children)
                        {
                            val temp2: ArrayList<workoutplan> = ArrayList<workoutplan>()
                                for (v in ds.children) {
                                    val temp: ArrayList<String> = ArrayList<String>()
                                    for (x in v.children) {
                                        temp.add(x.value.toString())
                                    }
                                    val n = workoutplan(v.key.toString(),temp)
                                    temp2.add(n)
                                }
                            val m = workoutPlanList(ds.key, temp2)
                            list?.add(m)
                        }
                        /*change program or workout*/
                        var adapterC: AdaptoWList = AdaptoWList(list!!,this@ChangeProgram)
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
    /*WorkoutPlan : workoutplanlis*/
    override fun ClickedItem(WorkoutPlanList : workoutPlanList) {
        userReference?.child("workout plans")?.child(WorkoutPlanList.workoutid.toString())?.setValue(WorkoutPlanList.inside)
        Thread.sleep(50)
        Thread.sleep(50)
        finish()
    }
}
