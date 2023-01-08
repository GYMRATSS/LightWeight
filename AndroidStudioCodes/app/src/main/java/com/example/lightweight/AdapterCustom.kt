package com.example.lightweight

import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.AdapterCustom.MyViewHolderWorkout
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView


class AdapterCustom(var programlist: ArrayList<workoutplancustom>, var clickListener: AdapterCustom.ClickListener) : RecyclerView.Adapter<AdapterCustom.MyViewHolderWorkout>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCustom.MyViewHolderWorkout {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_customprogram, parent, false)
        return MyViewHolderWorkout(view)
    }

    override fun onBindViewHolder(holder_customprogram: AdapterCustom.MyViewHolderWorkout, position: Int) {
        val Modal = programlist[position]
        holder_customprogram.planid_.text = programlist[position].id
        Size_programlist.mySizePL = programlist.size
        holder_customprogram.descr.text = "Ağırlık: " + programlist[position].ağırlık +"\n"+
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

    interface ClickListener{
        fun ClickedItem(workoutplancustom: workoutplancustom)
    }

}