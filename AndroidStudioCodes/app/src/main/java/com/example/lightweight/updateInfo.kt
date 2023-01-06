package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.example.lightweight.databinding.ActivityUpdateInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.math.log10

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
                binding.updateHip.setText(snapshot.child("Kalça").value.toString())
                //Holders
                binding.cinsiyetHolder1.setText(snapshot.child("Cinsiyet").value.toString())
                binding.hareketHolder.setText(snapshot.child("Hareket seviyesi").value.toString())
                binding.tercihHolder1.setText(snapshot.child("Tercih").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //bilgileri güncelleme
        binding.guncelleButonu.setOnClickListener{
            var uyeAd = binding.updateName.text.toString().trim()
            var uyeYas = binding.updateAge.text.toString().trim()
            var uyeKilo = binding.updateWeight.text.toString().trim()
            var uyeBoy = binding.updateHeight.text.toString().trim()
            var uyeBel = binding.updateWaist.text.toString().trim()
            var uyeBoyun = binding.updateNeck.text.toString().trim()
            var uyeKalca = binding.updateHip.text.toString().trim()
            var uyeHedefKilo = binding.updateGoalWeight.text.toString().trim()

            var currentUserDb = databaseReference?.child(currentUser?.uid!!)


            //realtimedatabase ad soyad güncelleme
            currentUserDb?.child("İsim-soyisim")?.removeValue()
            currentUserDb?.child("İsim-soyisim")?.setValue(uyeAd)
            if(uyeYas.toInt()>0){
                currentUserDb?.child("Yaş")?.removeValue()
                currentUserDb?.child("Yaş")?.setValue(uyeYas)}
            if(uyeKilo.toInt()>0){
                currentUserDb?.child("Kilo")?.removeValue()
                currentUserDb?.child("Kilo")?.setValue(uyeKilo)}
            if(uyeBoy.toInt()>0){
                currentUserDb?.child("Boy")?.removeValue()
                currentUserDb?.child("Boy")?.setValue(uyeBoy)}
            if(uyeBel.toInt()>0){
                currentUserDb?.child("Bel")?.removeValue()
                currentUserDb?.child("Bel")?.setValue(uyeBel)}
            if(uyeBoyun.toInt()>0){
                currentUserDb?.child("Boyun")?.removeValue()
                currentUserDb?.child("Boyun")?.setValue(uyeBoyun)}
            if(uyeHedefKilo.toInt()>0){
                currentUserDb?.child("Hedef")?.removeValue()
                currentUserDb?.child("Hedef")?.setValue(uyeHedefKilo)}
            if(uyeKalca.toInt()>0){
                currentUserDb?.child("Kalça")?.removeValue()
                currentUserDb?.child("Kalça")?.setValue(uyeKalca)}

            //kalori ihtiyacı - yağ oranı - BMI - BMR
            var number = ((uyeKilo.toDouble()/(uyeBoy.toDouble()*uyeBoy.toDouble()))*10000.0)
            var uyeBMI = String.format("%.2f", number)
            var uyeCinsiyet = binding.cinsiyetHolder1.text.toString()
            var uyeHedefTercih = binding.tercihHolder1.text.toString()
            var uyeHareketTercih = binding.hareketHolder.text.toString()
            var uyeKaloriIhtiyaci = ""
            var uyeYagOrani = ""
            var uyeBMR = ""

            if(uyeCinsiyet == "Erkek"){
                uyeBMR = (66.4730 + 13.7516*(uyeKilo.toDouble()) + 5.0033*(uyeBoy.toDouble()) + (-6.7550*(uyeYas.toDouble()))).toString()
                uyeYagOrani = (495/(1.0324 - 0.19077* log10(uyeBel.toDouble()-uyeBoyun.toDouble()) + 0.15456* log10(uyeBoy.toDouble())) - 450).toString()
            }else if (uyeCinsiyet == "Kadın"){
                uyeBMR= (655.0955 + 9.5634*(uyeKilo.toDouble()) + 1.8496*(uyeBoy.toDouble()) + (-4.6756*(uyeYas.toDouble()))).toString()
                uyeYagOrani = (495/(1.29579 - 0.35004* log10(uyeBel.toDouble() + uyeKalca.toDouble()-uyeBoyun.toDouble()) + 0.221* log10(uyeBoy.toDouble())) - 450).toString()
            }else { //Treated as woman
                uyeBMR= (655.0955 + 9.5634*(uyeKilo.toDouble()) + 1.8496*(uyeBoy.toDouble()) + (-4.6756*(uyeYas.toDouble()))).toString()
                uyeYagOrani = (495/(1.29579 - 0.35004* log10(uyeBel.toDouble() + uyeKalca.toDouble()-uyeBoyun.toDouble()) + 0.221* log10(uyeBoy.toDouble())) - 450).toString()
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


            currentUserDb?.child("BMI")?.setValue(uyeBMI)
            currentUserDb?.child("BMR")?.setValue(uyeBMR)
            currentUserDb?.child("Yağ oranı")?.setValue(uyeYagOrani)
            currentUserDb?.child("Kalori ihtiyacı")?.setValue(uyeKaloriIhtiyaci)

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