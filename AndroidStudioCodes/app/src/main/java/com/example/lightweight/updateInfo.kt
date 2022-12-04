package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.example.lightweight.databinding.ActivityUpdateInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class updateInfo : AppCompatActivity() {

    lateinit var binding:ActivityUpdateInfoBinding
    private lateinit var auth: FirebaseAuth /**/
    var databaseReference: DatabaseReference? = null /**/
    var database: FirebaseDatabase? = null /**/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUpdateInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() /**/
        database = FirebaseDatabase.getInstance() /**/
        databaseReference = database?.reference!!.child("Kullanıcılar")
        var currentUser = auth.currentUser

        //Kullanıcının id'sini alıyoruz
        var userReference = databaseReference?.child(currentUser?.uid!!)

        userReference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.updateName.setText(snapshot.child("İsim-soyisim").value.toString())
                binding.updateAge.setText(snapshot.child("Yaş").value.toString())
                binding.updateWeight.setText(snapshot.child("Kilo").value.toString())
                binding.updateHeight.setText(snapshot.child("Boy").value.toString())
                binding.updateWaist.setText(snapshot.child("Bel").value.toString())
                binding.updateNeck.setText(snapshot.child("Boyun").value.toString())
                binding.updateGoalWeight.setText(snapshot.child("Hedef").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //bilgileri güncelleme
        binding.guncelleButonu.setOnClickListener{
            var newName = binding.updateName.text.toString().trim()
            var newAge = binding.updateAge.text.toString().trim()
            var newWeight = binding.updateWeight.text.toString().trim()
            var newHeight = binding.updateHeight.text.toString().trim()
            var newWaist = binding.updateWaist.text.toString().trim()
            var newNeck = binding.updateNeck.text.toString().trim()
            var newGoalWeight = binding.updateGoalWeight.text.toString().trim()

            var currentUserDb = databaseReference?.child(currentUser?.uid!!)


            //realtimedatabase ad soyad güncelleme
            currentUserDb?.child("İsim-soyisim")?.removeValue()
            currentUserDb?.child("İsim-soyisim")?.setValue(newName)
            currentUserDb?.child("Yaş")?.removeValue()
            currentUserDb?.child("Yaş")?.setValue(newAge)
            currentUserDb?.child("Kilo")?.removeValue()
            currentUserDb?.child("Kilo")?.setValue(newWeight)
            currentUserDb?.child("Boy")?.removeValue()
            currentUserDb?.child("Boy")?.setValue(newHeight)
            currentUserDb?.child("Bel")?.removeValue()
            currentUserDb?.child("Bel")?.setValue(newWaist)
            currentUserDb?.child("Boyun")?.removeValue()
            currentUserDb?.child("Boyun")?.setValue(newNeck)
            currentUserDb?.child("Hedef")?.removeValue()
            currentUserDb?.child("Hedef")?.setValue(newGoalWeight)

            Toast.makeText(applicationContext,"Bilgileriniz güncellendi.", Toast.LENGTH_LONG).show()


        }

        val actionBar = supportActionBar
        actionBar!!.hide()

        val closeButton = findViewById<ImageButton>(R.id.closeUpdateInfo)

        closeButton.setOnClickListener {
            val intent = Intent (this, Profile::class.java)
            startActivity(intent)
        }

    }
}