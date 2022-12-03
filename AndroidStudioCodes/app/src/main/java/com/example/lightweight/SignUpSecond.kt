package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
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

        var currentUser = auth.currentUser
        var userReference = databaseReference?.child(currentUser?.uid!!)

        userReference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.startHeader.text = "Başlayalım " + snapshot.child("İsim-soyisim").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //radio buton okuma
        val rdGroup = findViewById<RadioGroup>(R.id.radioChoose)
        val rdGroup2 = findViewById<RadioGroup>(R.id.activityLevelRadioGroup)

        //kaydet butonu ile kaydetme adımları
        binding.skipButton.setOnClickListener{
            val slcBtn:Int = rdGroup!!.checkedRadioButtonId
            var uyeTercih = ""

            val slcBtn2:Int = rdGroup2!!.checkedRadioButtonId
            var uyeTercih2 = ""

            if(findViewById<RadioButton>(slcBtn)!=null && findViewById<RadioButton>(slcBtn2)!=null ){
                uyeTercih = findViewById<RadioButton>(slcBtn).text.toString()
                uyeTercih2 = findViewById<RadioButton>(slcBtn2).text.toString()

            } else {
                Toast.makeText(this@SignUpSecond,"Lütfen birini işaretleyin.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var uyeKilo = binding.weightInput.text.toString()
            var uyeBoy = binding.heightInput.text.toString()
            var uyeBel = binding.waistInput.text.toString()
            var uyeBoyun = binding.neckInput.text.toString()
            var uyeHedef = binding.goalInput.text.toString()
            var number = 3.14
            var uyeBMI = ""


            if(TextUtils.isEmpty(uyeKilo) || uyeKilo.toInt()<1){
                binding.weightInput.error = "Lütfen kilonuzu doğru giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeBoy) || uyeBoy.toInt()<1){
                binding.heightInput.error = "Lütfen boyunuzu doğru giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeBel) || uyeBel.toInt()<1){
                binding.waistInput.error = "Lütfen bel ölçünüzü doğru giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeBoyun) || uyeBoyun.toInt()<1){
                binding.neckInput.error = "Lütfen boyun çevrenizi doğru giriniz."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(uyeHedef) || uyeHedef.toInt()<1){
                binding.goalInput.error = "Lütfen uygun bir hedef giriniz."
                return@setOnClickListener
            } else {
                number = ((uyeKilo.toDouble()/(uyeBoy.toDouble()*uyeBoy.toDouble()))*10000.0)
                uyeBMI = String.format("%.2f", number)
            }


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
            currentUserDb?.child("Hareket seviyesi")?.setValue(uyeTercih2)


            //Profil sayfasına gitmek için
                intent = Intent(applicationContext, Profile::class.java)
                startActivity(intent)
                finish() //Bu activity yi kapatıyor.

        }

    }
}