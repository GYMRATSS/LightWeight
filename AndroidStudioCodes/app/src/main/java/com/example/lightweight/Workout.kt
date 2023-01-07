package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate
//import com.example.lightweight.Workout
object Size_programlist{

    var mySizePL = 1
    var clickCount = 0
    var clickCountFinish = 0
}

class Workout : AppCompatActivity(), AdaptoWList.ClickListener {

    var ref: DatabaseReference? = null /**/

    var recView : RecyclerView? = null

   /* private lateinit var auth: FirebaseAuth /**/
    var databaseReference: DatabaseReference? = null /**/
    var database: FirebaseDatabase? = null /**/*/
    //var myvar = 0

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
        ref = userReference?.child("workout plans")
        recView = findViewById(R.id.moves)
        var list: ArrayList<workoutplan>? = ArrayList<workoutplan>()
        var i = 0
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for(ds in snapshot.children)
                        {

                            for (v in ds.children) {
                                val temp: ArrayList<String> = ArrayList<String>()
                                for (x in v.children) {
                                    temp.add(x.value.toString())
                                }
                                val m = workoutplan(temp[3], temp)
                                list?.add(m)
                            }
                            val adapterC: AdapterClassWorkout = AdapterClassWorkout(list!!)
                            recView?.adapter = adapterC
                            break
                        }
                    }
                    calculationw()
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

        val changep:  Button = findViewById(R.id.changeprogrambutton)
        ref = userReference?.child("workout plans")
        recView = findViewById(R.id.moves)
        changep.setOnClickListener() {
            /*Size_programlist.mySizePL = 1
            Size_programlist.clickCount = 0
            ref?.removeValue()*/
            val intent = Intent(this, ChangeProgram::class.java)
            startActivity(intent)
        }



        var list: ArrayList<workoutplan>? = ArrayList<workoutplan>()
        var i = 0
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        Thread.sleep(100)
                        for (ds in snapshot.children)
                        {
                            for (v in ds.children) {
                                val temp: ArrayList<String> = ArrayList<String>()
                                for (x in v.children) {
                                    temp.add(x.value.toString())
                                }
                                val m = workoutplan(temp[1], temp)
                                list?.add(m)
                            }
                            val adapterC: AdapterClassWorkout = AdapterClassWorkout(list!!)
                            recView?.adapter = adapterC
                            break
                        }
                    }
                    calculationw()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Workout, error.message, Toast.LENGTH_SHORT).show()
                }
            })

        }



    }

    fun calculationw(){
        var w_bar: ProgressBar = findViewById (R.id.workoutProgressBar)
        var wPercent: TextView = findViewById (R.id.w_persent)
        if(w_bar?.progress!! < 100) w_bar?.progress = ((Size_programlist.clickCount)*100)/Size_programlist.mySizePL
        val percent =  ((Size_programlist.clickCount)*100)/Size_programlist.mySizePL
        wPercent?.text = "%$percent"

    }

    override fun ClickedItem(workoutPlanList: workoutPlanList) {

    }

}