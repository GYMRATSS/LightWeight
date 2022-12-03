package com.example.lightweight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class AddFoodToUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food_to_user)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val prePage: ImageButton = findViewById(R.id.prePage)

        prePage.setOnClickListener() {
            finish()
        }
    }
}