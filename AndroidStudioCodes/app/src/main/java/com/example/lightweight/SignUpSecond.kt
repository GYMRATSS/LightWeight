package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.example.lightweight.databinding.ActivitySignUpSecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.math.log10

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
                //yaş bilgisini görünmez bir textte depoluyoruz
                binding.ageHolder.text = snapshot.child("Yaş").value.toString()
                binding.cinsiyetHolder.text = snapshot.child("Cinsiyet").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //radio buton okuma
        val rdGroup = findViewById<RadioGroup>(R.id.radioChoose)
        val rdGroup2 = findViewById<RadioGroup>(R.id.activityLevelRadioGroup)

        //next butonu ile kaydetme adımları
        binding.skipButton.setOnClickListener{
            val slcBtn:Int = rdGroup!!.checkedRadioButtonId
            var uyeHedefTercih = ""

            val slcBtn2:Int = rdGroup2!!.checkedRadioButtonId
            var uyeHareketTercih = ""

            if(findViewById<RadioButton>(slcBtn)!=null && findViewById<RadioButton>(slcBtn2)!=null ){
                uyeHedefTercih = findViewById<RadioButton>(slcBtn).text.toString()
                uyeHareketTercih = findViewById<RadioButton>(slcBtn2).text.toString()

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
            var uyeBMR = ""
            var uyeKaloriIhtiyaci = ""
            var uyeYagOrani = ""



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
                //Inputlar doğru ise hesaplama yapılır
                number = ((uyeKilo.toDouble()/(uyeBoy.toDouble()*uyeBoy.toDouble()))*10000.0)
                uyeBMI = String.format("%.2f", number)

                //Harris-Benedict equation formula for calorie calculation
                //men= 66.4730 + 13.7516 x weight in kg + 5.0033 x height in cm – 6.7550 x age
                //women= 655.0955 + 9.5634 x weight in kg + 1.8496 x height in cm – 4.6756 x age
                //https://www.ncbi.nlm.nih.gov/pmc/articles/PMC7784146/
                //https://www.thejakartapost.com/life/2016/09/27/how-to-calculate-your-ideal-calorie-intake.html#:~:text=Formula%20used%20to%20calculate%20men's,divided%20by%204.7%20x%20age.

                //Measuring Body Fat Percentage According to U.S. Navy Method

                var uyeYas = binding.ageHolder.text.toString()
                var uyeCinsiyet = binding.cinsiyetHolder.text.toString()

                if(uyeCinsiyet == "Erkek"){
                    uyeBMR = (66.4730 + 13.7516*(uyeKilo.toDouble()) + 5.0033*(uyeBoy.toDouble()) + (-6.7550*(uyeYas.toDouble()))).toString()
                    uyeYagOrani = (495/(1.0324 - 0.19077*log10(uyeBel.toDouble()-uyeBoyun.toDouble()) + 0.15456*log10(uyeBoy.toDouble())) - 450).toString()
                }else if (uyeCinsiyet == "Kadın"){
                    uyeBMR= (655.0955 + 9.5634*(uyeKilo.toDouble()) + 1.8496*(uyeBoy.toDouble()) + (-4.6756*(uyeYas.toDouble()))).toString()
                    uyeYagOrani = (495/(1.0324 - 0.19077*log10(uyeBel.toDouble()-uyeBoyun.toDouble()) + 0.15456*log10(uyeBoy.toDouble())) - 450).toString()
                }else { //Treated as woman
                    uyeBMR= (655.0955 + 9.5634*(uyeKilo.toDouble()) + 1.8496*(uyeBoy.toDouble()) + (-4.6756*(uyeYas.toDouble()))).toString()
                    uyeYagOrani = (495/(1.0324 - 0.19077*log10(uyeBel.toDouble()-uyeBoyun.toDouble()) + 0.15456*log10(uyeBoy.toDouble())) - 450).toString()
                }

                if(uyeHareketTercih == "Az"){
                    uyeKaloriIhtiyaci = (uyeBMR.toDouble()*1.2).toString()
                }else if(uyeHareketTercih == "Orta"){
                    uyeKaloriIhtiyaci = (uyeBMR.toDouble()*1.3).toString()
                }else{
                    uyeKaloriIhtiyaci = (uyeBMR.toDouble()*1.4).toString()
                }

                if(uyeHedefTercih == "Kilo al"){
                    uyeKaloriIhtiyaci = (uyeKaloriIhtiyaci.toDouble() + 400.0).toString()
                }else if(uyeHedefTercih == "Kilo ver"){
                    uyeKaloriIhtiyaci = (uyeKaloriIhtiyaci.toDouble() - 400.0).toString()
                }else{
                    //nothing
                }


                //Küsürattan kurtulmak
                uyeBMR= String.format("%.0f", uyeBMR.toDouble())
                uyeKaloriIhtiyaci = String.format("%.0f", uyeKaloriIhtiyaci.toDouble())
                uyeYagOrani = String.format("%.2f", uyeYagOrani.toDouble())
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
            currentUserDb?.child("Tercih")?.setValue(uyeHedefTercih)
            currentUserDb?.child("Hedef")?.setValue(uyeHedef)
            currentUserDb?.child("BMI")?.setValue(uyeBMI)
            currentUserDb?.child("BMR")?.setValue(uyeBMR)
            currentUserDb?.child("Hareket seviyesi")?.setValue(uyeHareketTercih)
            currentUserDb?.child("Kalori ihtiyacı")?.setValue(uyeKaloriIhtiyaci)
            currentUserDb?.child("Yağ oranı")?.setValue(uyeYagOrani)
            currentUserDb?.child("İlk kilo")?.setValue(uyeYagOrani)


            //Profil sayfasına gitmek için
                intent = Intent(applicationContext, Profile::class.java)
                startActivity(intent)
                finish() //Bu activity yi kapatıyor.

        }

    }
}