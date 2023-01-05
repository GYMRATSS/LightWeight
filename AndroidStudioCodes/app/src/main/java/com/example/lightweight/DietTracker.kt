package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DietTracker : AppCompatActivity(){

    var ref: DatabaseReference? = null /**/
    var theListView : ListView? = null
    var theCalendarView: CalendarView? =null
    var auth = FirebaseAuth.getInstance() /**/
    var database = FirebaseDatabase.getInstance() /**/
    var databaseReference = database?.reference!!.child("Kullanıcılar") /**/
    var currentUser = auth.currentUser
    var userReference = databaseReference?.child(currentUser?.uid!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_tracker)

        val actionBar = supportActionBar
        actionBar!!.hide()

        theListView = findViewById(R.id.listView1)
        theCalendarView = findViewById<CalendarView>(R.id.calendarView)

        val closeButton = findViewById<ImageButton>(R.id.closeDietTracker)
        closeButton.setOnClickListener {
            val intent = Intent (this, Profile::class.java)
            startActivity(intent)
        }

        // baslangicta today list icin
        ref = userReference?.child("besin")?.child("besin kayıtları")?.child(LocalDate.now().toString())
        var list = mutableListOf<String>()

        if (ref != null) {
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (ds in snapshot.children) {
                            val meal = ds.getValue(meal::class.java)
                            if (meal != null) {
                                list.add(meal.id!!)
                            }
                        }
                    }

                    val mealListAsArray = list.toTypedArray()
                    val listadp = ArrayAdapter(this@DietTracker, android.R.layout.simple_list_item_1, mealListAsArray)
                    theListView?.adapter = listadp
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@DietTracker, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }


    override fun onResume() {
        super.onResume()

        theListView = findViewById(R.id.listView1)
        theCalendarView = findViewById<CalendarView>(R.id.calendarView)

        theCalendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val selectedDate = GregorianCalendar(year, month, dayOfMonth).time
            val dateString = dateFormat.format(selectedDate)

            ref = userReference?.child("besin")?.child("besin kayıtları")?.child(dateString)
            var list = mutableListOf<String>()

            if (ref != null) {
                ref!!.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (ds in snapshot.children) {
                                val meal = ds.getValue(meal::class.java)
                                if (meal != null) {
                                    list.add(meal.id!!)
                                }
                            }
                        }

                        val mealListAsArray = list.toTypedArray()
                        val listadp = ArrayAdapter(this@DietTracker, android.R.layout.simple_list_item_1, mealListAsArray)
                        theListView?.adapter = listadp
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@DietTracker, error.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }


    }

}
