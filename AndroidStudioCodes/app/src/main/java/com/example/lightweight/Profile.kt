package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.lightweight.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Profile : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth /**/
    var databaseReference: DatabaseReference? = null /**/
    var database: FirebaseDatabase? = null /**/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() /**/
        database = FirebaseDatabase.getInstance() /**/
        databaseReference = database?.reference!!.child("Kullanıcılar")
        var currentUser = auth.currentUser

        var userReference = databaseReference?.child(currentUser?.uid!!)

        val actionBar = supportActionBar
        actionBar!!.hide()

        userReference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.age.text = snapshot.child("Yaş").value.toString()
                binding.gender.text = snapshot.child("Cinsiyet").value.toString()
                binding.weight.text = snapshot.child("Kilo").value.toString()
                binding.height.text = snapshot.child("Boy").value.toString()
                binding.waist.text = snapshot.child("Bel").value.toString()
                binding.neck.text = snapshot.child("Boyun").value.toString()
                binding.bmi.text = snapshot.child("BMI").value.toString()
                binding.start.text = snapshot.child("Kilo").value.toString()
                binding.goal.text = snapshot.child("Hedef").value.toString()
                binding.userName.text = snapshot.child("İsim-soyisim").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //çıkış yap butonu
        binding.logOut.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this@Profile,MainActivity::class.java))
            finish()
        }
        //kalori takibi
        binding.logOut.setOnClickListener{
            startActivity(Intent(this@Profile,CalorieCount::class.java))
            finish()
        }


    }
}