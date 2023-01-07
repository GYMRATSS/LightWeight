package com.example.lightweight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CustomProgram : AppCompatActivity() , AdaptoWList.ClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_program2)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun ClickedItem(workoutPlanList: workoutPlanList) {
        TODO("Not yet implemented")
    }
}