package com.example.lightweight

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class EnterFood : AppCompatActivity(), AdapterClass.ClickListener{
    var ref: DatabaseReference? = null /**/
    var list: ArrayList<meal>? = ArrayList<meal>()
    var recView : RecyclerView? = null
    private var sView : SearchView? = null

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
        val intent = Intent(this@EnterFood, AddFoodToUser::class.java)
        startActivity(intent)
    }

}
