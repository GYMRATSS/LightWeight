package com.example.lightweight

import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.AdaptoWList.MyViewHolderWorkout
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class AdaptoWList(var programlist: ArrayList<workoutPlanList>, var clickListener: ClickListener) : RecyclerView.Adapter<MyViewHolderWorkout>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderWorkout {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_program, parent, false)
        return MyViewHolderWorkout(view)
    }

    override fun onBindViewHolder(holder_program: MyViewHolderWorkout, position: Int) {
        val Modal = programlist[position]
        holder_program.planid_.text = programlist[position].workoutid
        for (i in 0 until programlist[position].inside?.size!!)
        {
            holder_program.descr.text = "İsim: " + programlist[position].inside?.get(i)?.workoutid.toString() + "\n" +  "Set: " +
                    programlist[position].inside?.get(i)?.set.toString() + "\n" +  "Ağırlık: " +
                        programlist[position].inside?.get(i)?.ağırlık.toString() + "\n" +  "Tekrar: " + programlist[position].inside?.get(i)?.tekrar.toString()
        }

        holder_program.itemView.setOnClickListener{
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
        fun ClickedItem(workoutPlanList: workoutPlanList)
    }


}