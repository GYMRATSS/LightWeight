package com.example.lightweight

import com.example.lightweight.meal
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.AdapterClass.MyViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.lightweight.R
import android.widget.TextView
import java.util.ArrayList

private class AdapterClass(var list: ArrayList<meal>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text = list[position].id
        holder.desc.text = list[position].kalori
    }

    override fun getItemCount(): Int {
        return list.size
    }

    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView
        var desc: TextView

        init {
            id = itemView.findViewById(R.id.foodId)
            desc = itemView.findViewById(R.id.descrb)
        }
    }
}