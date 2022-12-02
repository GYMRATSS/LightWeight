package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lightweight.databinding.ActivitySignUpFirstBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpFirst : AppCompatActivity() {
    lateinit var binding: ActivitySignUpFirstBinding /**/
    private lateinit var auth: FirebaseAuth /**/
    var databaseReference: DatabaseReference? = null /**/
    var database: FirebaseDatabase? = null /**/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpFirstBinding.inflate(layoutInflater) /**/
        setContentView(binding.root) /**/
        auth = FirebaseAuth.getInstance() /**/
        database = FirebaseDatabase.getInstance() /**/
        databaseReference = database?.reference!!.child("Kullanıcılar")

        val actionBar = supportActionBar
        actionBar!!.hide()

        //kaydet butonu ile kaydetme adımları
        binding.nextSignIn.setOnClickListener{
            var uyeCinsiyet=""
            var uyeAdSoyad = binding.nameInfoFromUser.text.toString()
            var uyeYas = binding.ageInfoFromUser.text.toString()
            var uyeEmail = binding.eMailInfo.text.toString()
            var uyeParola = binding.passwordInfo.text.toString()

            if(TextUtils.isEmpty(uyeAdSoyad)){
                binding.nameInfoFromUser.error = "Lütfen adınızı soyadınızı giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeEmail)){
                binding.eMailInfo.error = "Lütfen e mailinizi giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeParola)){
                binding.passwordInfo.error = "Lütfen parolanızı giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeYas)){
                binding.ageInfoFromUser.error = "Lütfen yaşınızı giriniz."
                return@setOnClickListener
            }

            //Email kullanıcı adı parola bilgilerini veri tabanına ekleme
            auth.createUserWithEmailAndPassword(uyeEmail, uyeParola)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        //Şu anki kullanıcı bilgilerini alalım
                        var currentUser = auth.currentUser

                        //Kullanıcı ID'sini alıp o ID altında kullanıcının adını ve soyadını kaydedelim.
                        var currentUserDb = databaseReference?.child(currentUser?.uid!!)
                        currentUserDb?.child("İsim-soyisim")?.setValue(uyeAdSoyad)
                        currentUserDb?.child("Yaş")?.setValue(uyeYas)
                        currentUserDb?.child("Cinsiyet")?.setValue(uyeCinsiyet)
                        currentUserDb?.child("Email")?.setValue(uyeEmail)
                        currentUserDb?.child("Parola")?.setValue(uyeParola)

                        //Eğer başarılıysa kullanıcıya mesaj veriyoruz.
                        Toast.makeText(this@SignUpFirst,"Kayıt başarılı.", Toast.LENGTH_LONG).show()

                        intent = Intent(applicationContext, SignUpSecond::class.java)
                        startActivity(intent) //İkinci sayfasını açıyor
                        finish() //Bu activity yi kapatıyor.

        //im hereeeeeeee

                    } else {
                        Toast.makeText(this@SignUpFirst,"Kayıt hatalı.", Toast.LENGTH_LONG).show()
                    }
                }

        }





    }
}