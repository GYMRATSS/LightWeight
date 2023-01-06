package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate

class CalorieCount : AppCompatActivity() {
    var ref: DatabaseReference? = null /**/
    var recView : RecyclerView? = null

    // TUÇEMİ SEVİORUM

    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie_count)


        val actionBar = supportActionBar
        actionBar!!.hide()

        /******** water entry **********/
        var remainW = 2000

        val p_bar: ProgressBar = findViewById (R.id.waterProgressBar)
        val r_text: TextView = findViewById (R.id.remainWater_m)
        fun updateProgressBar(){
            p_bar.progress = ((2000-remainW)*100)/2000
            r_text.text = "$remainW ml"
        }

        val water: ImageButton = findViewById (R.id.glassButton)
        water.setOnClickListener() {
            if(remainW > 0){
                remainW -= 200
                updateProgressBar()
            }
        }


        /*********************************************************/

        ref = userReference?.child("besin")?.child("besin kayıtları")?.child(LocalDate.now().toString())
        recView = findViewById(R.id.foods)
        var list: ArrayList<meal>? = ArrayList<meal>()
        var takenCal = 0
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
                            val m = meal(ds.key, temp)
                            list?.add(m)*/
                            list?.add(ds.getValue(meal::class.java)!!)
                            takenCal += list?.get(list.size-1)?.kalori!!.toInt()
                        }
                        val adapterC: AdapterForList = AdapterForList(list!!)
                        recView?.adapter = adapterC
                    }
                    calculations(takenCal)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CalorieCount, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        val reset: ImageButton = findViewById (R.id.garbageButton)
        reset.setOnClickListener() {
            ref?.removeValue()
            val intent = Intent(this, CalorieCount::class.java)
            startActivity(intent)
            finish()
        }

        val newFood:  Button = findViewById(R.id.newFood)
        newFood.setOnClickListener() {
            val intent = Intent(this, EnterFood::class.java)
            startActivity(intent)
        }

        /******************** Menu ***************************************/
        val shp:  ImageButton = findViewById(R.id.shopage)

        shp.setOnClickListener() {
            val intent = Intent(this, Shop::class.java)
            startActivity(intent)
            finish()
        }

        val profilePageButton:  ImageButton = findViewById(R.id.userpage)

        profilePageButton.setOnClickListener() {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }

        val food:  ImageButton = findViewById(R.id.foodpage)
        food.isClickable = false

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        homeButton.setOnClickListener {
            val intent = Intent(this, homePage::class.java)
            startActivity(intent)
            finish()
        }
        val gymButton = findViewById<ImageButton>(R.id.gympage)
        gymButton.setOnClickListener {

            val intent = Intent (this, Workout::class.java)
            startActivity(intent)
            finish()

        }

        /**************************************************/


    }


    override fun onResume() {
        super.onResume()

        /*********************************************************/
        val newFood:  Button = findViewById(R.id.newFood)
        newFood.setOnClickListener() {
            val intent = Intent(this, EnterFood::class.java)
            startActivity(intent)
        }
        ref = userReference?.child("besin")?.child("besin kayıtları")?.child(LocalDate.now().toString())
        recView = findViewById(R.id.foods)

        userReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var a:TextView = findViewById (R.id.totalCalVal)
                a.text = snapshot.child("Kalori ihtiyacı").value.toString()

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        var list: ArrayList<meal>? = ArrayList<meal>()
        var takenCal = 0
        var takenCarb = 0
        var takenFat = 0
        var takenProtein = 0
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        Thread.sleep(100)
                        for (ds in snapshot.children)
                        {
                            /*val temp: ArrayList<String> = ArrayList<String>()
                            for (v in ds.children) {
                                temp.add(v.value.toString())
                            }
                            val m = meal(ds.key, temp)
                            list?.add(m)*/
                            list?.add(ds.getValue(meal::class.java)!!)
                            takenCal += list?.get(list.size-1)?.kalori!!.toInt()
                            takenCarb += list?.get(list.size-1)?.karbonhidrat!!.toDouble().toInt()
                            takenFat += list?.get(list.size-1)?.yağ!!.toDouble().toInt()
                            takenProtein += list?.get(list.size-1)?.protein!!.toDouble().toInt()
                        }
                        val adapterC: AdapterForList = AdapterForList(list!!)
                        recView?.adapter = adapterC
                    }
                    var b:TextView = findViewById (R.id.carbVal)
                    b.text = "$takenCarb gr"
                    var c:TextView = findViewById (R.id.fatVal)
                    c.text = "$takenFat gr"
                    var d:TextView = findViewById (R.id.proteinVal)
                    d.text = "$takenProtein gr"
                    calculations(takenCal)
                    userReference?.child("besin")?.child("makro kayıtları")?.child(LocalDate.now().toString())?.child("Karbonhidrat")?.setValue(takenCarb)
                    userReference?.child("besin")?.child("makro kayıtları")?.child(LocalDate.now().toString())?.child("Yağ")?.setValue(takenFat)
                    userReference?.child("besin")?.child("makro kayıtları")?.child(LocalDate.now().toString())?.child("Protein")?.setValue(takenProtein)

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CalorieCount, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    fun calculations(takenCal: Int){




        //Aşağıdaki satırlar üstte listener'ın içindeydi
        var c_bar: ProgressBar = findViewById (R.id.caloryProgressBar)
        var tc_text: TextView= findViewById (R.id.takenCalVal)
        var ttc_text:TextView = findViewById (R.id.totalCalVal)
        var rc_text: TextView = findViewById (R.id.remainCalVal)
        var cPercent: TextView = findViewById (R.id.c_persent)

        var totalCal = ttc_text?.text.toString().toIntOrNull() ?: 0 //TODO with firebase

        var remain = totalCal - takenCal
        if(c_bar?.progress!! < 100) c_bar?.progress = (takenCal*100)/totalCal
        val percent = (takenCal*100)/totalCal
        cPercent?.text = "%$percent"
        tc_text?.text = "$takenCal"
        remain = totalCal - takenCal
        rc_text?.text = "$remain"
        Thread.sleep(100)
        userReference?.child("besin")?.child("kalori kayıtları")?.child(LocalDate.now().toString())?.child("Alınan kalori")?.setValue(takenCal)
        userReference?.child("besin")?.child("kalori kayıtları")?.child(LocalDate.now().toString())?.child("Kalan kalori")?.setValue(remain)





    }

}