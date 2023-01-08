package com.example.lightweight

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lightweight.databinding.ActivityPicBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


private const val REQUEST_CODE_IMAGE_PICK = 0


class pic : AppCompatActivity() {
    private lateinit var binding: ActivityPicBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    var curFile: Uri? = null

    val imageRef = Firebase.storage.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        downloadImage()

        binding.btnPickImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }

        binding.btnUploadImage.setOnClickListener {
            uploadImageToStorage()
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent (this, updateInfo::class.java)
            startActivity(intent)
            finish()
        }
        binding.closeUpdateInfo2.setOnClickListener {
            val intent = Intent (this, Profile::class.java)
            startActivity(intent)
            finish()
        }

        val actionBar = supportActionBar
        actionBar!!.hide()
    }

    private fun downloadImage() = CoroutineScope(Dispatchers.IO).launch {
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
                binding.ivImage.setImageBitmap(bmp)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                //Toast.makeText(this@updatePic, e.message, Toast.LENGTH_LONG).show()
                binding.ivImage.setImageResource(R.drawable.person)

            }
        }
    }

    private fun uploadImageToStorage() = CoroutineScope(Dispatchers.IO).launch {
        try {
            auth = FirebaseAuth.getInstance()
            val uid = auth.currentUser?.uid
            curFile?.let {
                imageRef.child("Users/$uid").putFile(it).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@pic, "Fotoğraf başarıyla yüklendi.",
                        Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@pic, "Fotoğrafınız yüklenirken bir sorun meydana geldi.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK) {
            data?.data?.let {
                curFile = it
                binding.ivImage.setImageURI(it)
            }
        }
    }
}