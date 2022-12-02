package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.lightweight.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        //Kullanıcının oturumu açık mı değil mi kontrol edelim
        var currentUser = auth.currentUser


        val actionBar = supportActionBar
        actionBar!!.hide()

        //Giriş yap butonuna tıklandığında
        binding.logIn.setOnClickListener{
            var girisEmail = binding.editTextTextEmailAddress.text.toString()
            var girisParola = binding.editTextTextPassword.text.toString()

            //Kontrol
            if(TextUtils.isEmpty(girisEmail)){
                binding.editTextTextEmailAddress.error = "Lütfen email adresinizi yazın."
                return@setOnClickListener
            } else if(TextUtils.isEmpty(girisParola)){
                binding.editTextTextPassword.error = "Lütfen parolanızı yazın."
                return@setOnClickListener
            }

            //Giriş bilgilerini doğrulama
            auth.signInWithEmailAndPassword(girisEmail,girisParola)
                .addOnCompleteListener(this){ //it adında task resultu oluşturdu
                    if(it.isSuccessful){
                        intent = Intent(applicationContext,Profile::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext,"Giriş hatalı, lütfen tekrar deneyiniz.",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }

        //yeni üyelik sayfasıma gitmek için
        binding.signIn.setOnClickListener{
            intent = Intent(applicationContext,SignUpFirst::class.java)
            startActivity(intent)
            finish()
        }


    }
}