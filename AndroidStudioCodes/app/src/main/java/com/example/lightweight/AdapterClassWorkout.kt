package com.example.lightweight

import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.AdapterClassWorkout.MyViewHolderWorkout
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class AdapterClassWorkout(var programlist: ArrayList<workoutplan>, var clickListener: ClickListener) : RecyclerView.Adapter<MyViewHolderWorkout>() {
/*, var clickListener: ChangeProgram cikarildi*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderWorkout {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_program, parent, false)
        return MyViewHolderWorkout(view)
    }

    override fun onBindViewHolder(holder_program: MyViewHolderWorkout, position: Int) {
        val Modal = programlist[position]
        holder_program.planid_.text = programlist[position].workoutid
        holder_program.descr.text = "Hareket: " +programlist[position].workoutid + "\n" +  "Ağırlık: " + programlist[position].ağırlık +"\n"+
                "Set: " +programlist[position].set + "\n" + "Tekrar: " +programlist[position].tekrar + "\n"

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
        fun ClickedItem(workoutplan : workoutplan)
    }

}