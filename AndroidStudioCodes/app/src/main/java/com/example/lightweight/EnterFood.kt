package com.example.lightweight

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.databinding.ActivityEnterFoodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate


class EnterFood : AppCompatActivity(), AdapterClass.ClickListener{
    var ref: DatabaseReference? = null /**/
    var list: ArrayList<meal>? = ArrayList<meal>()
    var recView : RecyclerView? = null
    private var sView : SearchView? = null

    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_food)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val prePage: ImageButton = findViewById(R.id.prePage)

        prePage.setOnClickListener() {
            finish()
        }
        
        /********************************************/
        ref = FirebaseDatabase.getInstance().reference.child("yemekler")
        recView = findViewById(R.id.foodList)
        sView = findViewById(R.id.foodSearch)

    }

    override fun onStart(){
        super.onStart()
        if(ref != null){
            ref!!.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (ds in snapshot.children)
                        {
                            val temp: ArrayList<String> = ArrayList<String>()
                            for (v in ds.children) {
                                temp.add(v.value.toString())
                            }
                            val m = meal(ds.key, temp)
                            list?.add(m)
                        }
                        var adapterC: AdapterClass = AdapterClass(list!!,this@EnterFood)
                        recView?.adapter = adapterC
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@EnterFood, error.message, Toast.LENGTH_SHORT).show()
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
        val mylist: ArrayList<meal> = ArrayList<meal>()
        for (obj : meal in list!!)
        {
            if(obj.id?.lowercase()!!.contains(str!!.lowercase())){
                mylist.add(obj)
            }
        }
        var adapterC: AdapterClass = AdapterClass(mylist,this@EnterFood)
        recView?.adapter = adapterC

    }

    override fun ClickedItem(Meal: meal) {


        userReference?.child("besin")?.child("besin kayıtları")?.child(LocalDate.now().toString())?.child(Meal.id.toString())?.child("kalori")?.setValue(Meal.kalori)
        userReference?.child("besin")?.child("besin kayıtları")?.child(LocalDate.now().toString())?.child(Meal.id.toString())?.child("protein")?.setValue(Meal.protein)
        userReference?.child("besin")?.child("besin kayıtları")?.child(LocalDate.now().toString())?.child(Meal.id.toString())?.child("yağ")?.setValue(Meal.yağ)
        userReference?.child("besin")?.child("besin kayıtları")?.child(LocalDate.now().toString())?.child(Meal.id.toString())?.child("karbonhidrat")?.setValue(Meal.karbonhidrat)

        finish()

    }

}
