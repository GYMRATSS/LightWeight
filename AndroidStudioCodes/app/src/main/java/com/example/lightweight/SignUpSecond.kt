package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.lightweight.databinding.ActivitySignUpSecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignUpSecond : AppCompatActivity() {
    lateinit var binding: ActivitySignUpSecondBinding /**/
    private lateinit var auth: FirebaseAuth /**/
    var databaseReference: DatabaseReference? = null /**/
    var database: FirebaseDatabase? = null /**/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpSecondBinding.inflate(layoutInflater) /**/
        setContentView(binding.root) /**/
        auth = FirebaseAuth.getInstance() /**/
        database = FirebaseDatabase.getInstance() /**/
        databaseReference = database?.reference!!.child("Kullanıcılar")

        val actionBar = supportActionBar
        actionBar!!.hide()

        //kaydet butonu ile kaydetme adımları
        binding.skipButton.setOnClickListener{

            var uyeTercih = ""

            var uyeKilo = binding.weightInput.text.toString()
            var uyeBoy = binding.heightInput.text.toString()
            var uyeBel = binding.waistInput.text.toString()
            var uyeBoyun = binding.neckInput.text.toString()
            var uyeHedef = binding.goalInput.text.toString()
            var number = ((uyeKilo.toDouble()/(uyeBoy.toDouble()*uyeBoy.toDouble()))*10000.0)
            var uyeBMI = String.format("%.2f", number)

            //Doldurmayı zorunlu hale getiren kod satırları
            /*
            if(TextUtils.isEmpty(uyeAdSoyad)){
                binding.uyeAdSoyad.error = "Lütfen adınızı soyadınızı giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeEmail)){
                binding.uyeEmail.error = "Lütfen e mailinizi giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeParola)){
                binding.uyeParola.error = "Lütfen parolanızı giriniz."
                return@setOnClickListener
            }*/

            //Kullanıcı bilgilerini veri tabanına ekleme
            //Şu anki kullanıcı bilgilerini alalım
            var currentUser = auth.currentUser

            //Kullanıcı ID'sini alıp o ID altında kullanıcının adını ve soyadını kaydedelim.
            var currentUserDb = databaseReference?.child(currentUser?.uid!!)



            currentUserDb?.child("Kilo")?.setValue(uyeKilo)
            currentUserDb?.child("Boy")?.setValue(uyeBoy)
            currentUserDb?.child("Bel")?.setValue(uyeBel)
            currentUserDb?.child("Boyun")?.setValue(uyeBoyun)
            currentUserDb?.child("Tercih")?.setValue(uyeTercih)
            currentUserDb?.child("Hedef")?.setValue(uyeHedef)
            currentUserDb?.child("BMI")?.setValue(uyeBMI)


            //Profil sayfasına gitmek için
                intent = Intent(applicationContext, Profile::class.java)
                startActivity(intent)
                finish() //Bu activity yi kapatıyor.

        }

    }
}