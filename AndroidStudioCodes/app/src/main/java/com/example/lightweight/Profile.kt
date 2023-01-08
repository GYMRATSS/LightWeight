package com.example.lightweight

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.lightweight.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Profile : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth /**/
    var databaseReference: DatabaseReference? = null /**/
    var database: FirebaseDatabase? = null /**/
    val imageRef = Firebase.storage.reference


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

        fun downloadImage() = CoroutineScope(Dispatchers.IO).launch {
            try {
                auth = FirebaseAuth.getInstance()
                val uid = auth.currentUser?.uid
                val maxDownloadSize = 5L * 1024 * 1024
                val bytes = imageRef.child("Users/$uid").getBytes(maxDownloadSize).await()
                var bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                // Rotasyon
                /*val matrix = Matrix()
                matrix.postRotate(90F)
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)*/
                withContext(Dispatchers.Main) {
                    binding.imageView.setImageBitmap(bmp)
                }
            } catch(e: Exception) {
                withContext(Dispatchers.Main) {
                    //Toast.makeText(this@Profile, "Bilgileri güncelle sayfasından profil fotoğrafı yükleyebilirsiniz..", Toast.LENGTH_LONG).show()
                    binding.imageView.setImageResource(R.drawable.person)
                }
            }
        }
        downloadImage()

        userReference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.age.text = snapshot.child("Yaş").value.toString()
                binding.gender.text = snapshot.child("Cinsiyet").value.toString()
                binding.weight.text = snapshot.child("Kilo").value.toString()
                binding.height.text = snapshot.child("Boy").value.toString()
                binding.waist.text = snapshot.child("Bel").value.toString()
                binding.neck.text = snapshot.child("Boyun").value.toString()
                binding.bmi.text = snapshot.child("BMI").value.toString()
                binding.start.text = snapshot.child("İlk kilo").value.toString()
                binding.goal.text = snapshot.child("Hedef").value.toString()
                binding.fatRatio.text = snapshot.child("Yağ oranı").value.toString()
                binding.calorieIntake.text = snapshot.child("Kalori ihtiyacı").value.toString()
                binding.userName.text = snapshot.child("İsim-soyisim").value.toString()

                calculate()
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



        // initialWeight = userReference?.child("Kilo").toString().toInt()

       /* newWeight =
        weightGoal = binding.goal.text.toString().toInt() */
        //userReference?.child("Kilo")!!.setValue(newWeight)
        /*var initialWeight =
            userReference?.child("İlk Kilo")!!.setValue(newWeight)
        var weightGoal = userReference?.child("Hedef")*/


        /* initialWeight = binding.start.text.toString().toInt()
        newWeight = binding.weight.text.toString().toInt()
        weightGoal = binding.goal.text.toString().toInt() */




        val dietTrackerButton = findViewById<Button>(R.id.dietTracker)
        val workoutActivitiesButton = findViewById<Button>(R.id.workoutActivities)
        val qaButton = findViewById<Button>(R.id.qa)
        val logOutButton = findViewById<Button>(R.id.logOut)
        val editAccountButton = findViewById<Button>(R.id.editProfile)
        val editProfileButton = findViewById<Button>(R.id.editUser)

        val homeButton = findViewById<ImageButton>(R.id.homepage)
        val gymButton = findViewById<ImageButton>(R.id.gympage)
        val foodButton = findViewById<ImageButton>(R.id.foodpage)
        val shopButton = findViewById<ImageButton>(R.id.shopage)
        val profileButton = findViewById<ImageButton>(R.id.userpage)

        profileButton.isClickable = false

        editProfileButton.setOnClickListener {
            val intent = Intent (this, updateAccount::class.java)
            startActivity(intent)
        }

        editAccountButton.setOnClickListener {
            val intent = Intent (this, updateInfo::class.java)
            startActivity(intent)
        }

        dietTrackerButton.setOnClickListener {
            val intent = Intent (this, DietTracker::class.java)
            startActivity(intent)
        }

        workoutActivitiesButton.setOnClickListener {
            // history page must be implemented
        }

        qaButton.setOnClickListener {

            val intent = Intent (this, QandA::class.java)
            startActivity(intent)

        }
        logOutButton.setOnClickListener {

            // firebase stuff
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {

            val intent = Intent(this, homePage::class.java)
            startActivity(intent)

        }

        gymButton.setOnClickListener {


            val intent = Intent(this, Workout::class.java)
            startActivity(intent)

        }

        foodButton.setOnClickListener {

            val intent = Intent(this, CalorieCount::class.java)
            startActivity(intent)

        }

        shopButton.setOnClickListener {
            val intent = Intent(this, Shop::class.java)
            startActivity(intent)
        }


    }

    fun calculate(){
        val startWeightText: TextView = findViewById (R.id.start)
        val initialWeight = startWeightText?.text.toString().toIntOrNull() ?: 0

        val weightGoalText: TextView = findViewById (R.id.goal)
        val weightGoal = weightGoalText?.text.toString().toIntOrNull() ?: 0

        val currentWeightText: TextView = findViewById (R.id.weight)
        val currentWeight = currentWeightText?.text.toString().toIntOrNull() ?: 0

        var weightProgress = findViewById<ProgressBar>(R.id.weightProgressBar)

        if(weightProgress?.progress!! < 100)
            weightProgress?.progress = (Math.abs(currentWeight - initialWeight) * 100) / Math.abs(weightGoal - initialWeight)
    }

}