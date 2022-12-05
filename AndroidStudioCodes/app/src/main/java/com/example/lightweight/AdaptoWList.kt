package com.example.lightweight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptoWList(var programlist: ArrayList<workoutPlanList>, var clickListener: AdaptoWList.ClickListener) : RecyclerView.Adapter<AdaptoWList.MyViewHolderWorkout>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptoWList.MyViewHolderWorkout {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_program, parent, false)
        return MyViewHolderWorkout(view)
    }

    override fun onBindViewHolder(holder: AdaptoWList.MyViewHolderWorkout, position: Int) {
        val Modal = programlist[position]
        holder.planid_.text = programlist[position].workoutid
        holder.descr.text = programlist[position].name

        holder.itemView.setOnClickListener{
            clickListener.ClickedItem(Modal)
        }
    }

    override fun getItemCount(): Int {
        return programlist.size
    }

    inner class MyViewHolderWorkout(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var planid_: TextView
        var descr: TextView

        init {
            planid_ = itemView.findViewById(R.id.programId)
            descr = itemView.findViewById(R.id.description)
        }

    }

    interface ClickListener{
        fun ClickedItem(workoutplan : workoutPlanList)
    }


}