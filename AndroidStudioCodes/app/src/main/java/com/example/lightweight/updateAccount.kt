package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.example.lightweight.databinding.ActivityUpdateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class updateAccount : AppCompatActivity() {

    lateinit var binding:ActivityUpdateAccountBinding
    private lateinit var auth: FirebaseAuth /**/
    var databaseReference: DatabaseReference? = null /**/
    var database: FirebaseDatabase? = null /**/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() /**/
        database = FirebaseDatabase.getInstance() /**/
        databaseReference = database?.reference!!.child("Kullanıcılar")
        var currentUser = auth.currentUser

        //Kullanıcının id'sini alıyoruz
        var userReference = databaseReference?.child(currentUser?.uid!!)

        userReference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.updateEMailInfo.setText(snapshot.child("Email").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //bilgileri güncelleme
        binding.guncelleButonu.setOnClickListener{
            var newMail = binding.updateEMailInfo.text.toString().trim()
            var newParola1 = binding.updatePasswordInfo.text.toString().trim()
            var newParola2 = binding.updatePasswordInfoAgain.text.toString().trim()

            var currentUserDb = databaseReference?.child(currentUser?.uid!!)


            //Email güncelleme auth ve realtime db
            auth?.currentUser?.updateEmail(newMail)
                ?.addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(applicationContext,"E-mail güncellendi", Toast.LENGTH_LONG).show()
                        //Toast.makeText(applicationContext,newMail,Toast.LENGTH_LONG).show()
                        currentUserDb?.child("Email")?.removeValue()
                        currentUserDb?.child("Email")?.setValue(newMail)
                    } else {
                        Toast.makeText(applicationContext,"E-mail güncelleme başarısız", Toast.LENGTH_LONG).show()
                        //Toast.makeText(applicationContext,newMail,Toast.LENGTH_LONG).show()
                    }
                }
            //parola güncelleme auth ve realtime db
            if(newParola1 == newParola2) {
                auth?.currentUser?.updatePassword(newParola1)
                    ?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Parola güncellendi",
                                Toast.LENGTH_LONG
                            ).show()
                            //Toast.makeText(applicationContext,newParola,Toast.LENGTH_LONG).show()
                            currentUserDb?.child("Parola")?.removeValue()
                            currentUserDb?.child("Parola")?.setValue(newParola1)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Parola güncelleme başarısız, uygun bir parola giriniz.",
                                Toast.LENGTH_LONG
                            ).show()
                            //Toast.makeText(applicationContext,newParola,Toast.LENGTH_LONG).show()
                        }
                    }
            } else{
                Toast.makeText(
                    applicationContext,
                    "Parolalar eşleşmiyor.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.closeUpdateAccount.setOnClickListener {
            val intent = Intent (this, Profile::class.java)
            startActivity(intent)
        }
    }
}