package com.example.lightweight

import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.AdapterClass.MyViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class AdapterClass(var list: ArrayList<meal>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text = list[position].id
        holder.desc.text = "Kalori: " +list[position].kalori + "\n" +  "Karbonhidrat: " + list[position].karbonhidrat +"\n"+
                            "Yağ: " +list[position].yağ + "\n" + "Protein: " +list[position].protein + "\n"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView
        var desc: TextView

        init {
            id = itemView.findViewById(R.id.foodId)
            desc = itemView.findViewById(R.id.descrb)
        }
    }
}