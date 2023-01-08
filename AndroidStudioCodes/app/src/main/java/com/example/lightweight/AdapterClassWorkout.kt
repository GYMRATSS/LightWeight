package com.example.lightweight

import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.AdapterClassWorkout.MyViewHolderWorkout
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class AdapterClassWorkout(var programlist: ArrayList<workoutplan>) : RecyclerView.Adapter<MyViewHolderWorkout>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderWorkout {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_customprogram, parent, false)
        return MyViewHolderWorkout(view)
    }

    override fun onBindViewHolder(holder_customprogram: MyViewHolderWorkout, position: Int) {
        val Modal = programlist[position]
        holder_customprogram.planid_.text = programlist[position].id
        Size_programlist.mySizePL = programlist.size
        holder_customprogram.descr.text = "Hareket: " +programlist[position].id + "\n" +  "Ağırlık: " + programlist[position].ağırlık +"\n"+
                "Set: " +programlist[position].set + "\n" + "Tekrar: " +programlist[position].tekrar + "\n"
    }

    override fun getItemCount(): Int {
        return programlist.size
    }

    inner class MyViewHolderWorkout(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var planid_: TextView
        var descr: TextView

        init {
            planid_ = itemView.findViewById(R.id.customprogramId)
            descr = itemView.findViewById(R.id.customdescription)
        }



    }


}