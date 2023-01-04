package com.example.lightweight

import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.AdapterForList.MyViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class AdapterForList(var list: ArrayList<meal>) : RecyclerView.Adapter<AdapterForList.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_for_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val Modal = list[position]
        holder.id.text = list[position].id
        holder.desc.text = "Kalori: " +list[position].kalori
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