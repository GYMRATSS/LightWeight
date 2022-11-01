package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class Shop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val protein: Button = findViewById (R.id.protein)

        protein.setOnClickListener() {
            val intent = Intent(this, proteinShop::class.java)
            startActivity(intent)
        }

        val creatine: Button = findViewById (R.id.creatine)

        creatine.setOnClickListener() {
            val intent = Intent(this, creatineShop::class.java)
            startActivity(intent)
        }

        val eq: Button = findViewById (R.id.gymEquipments)

        eq.setOnClickListener() {
            val intent = Intent(this, Equipments::class.java)
            startActivity(intent)
        }


    }
}