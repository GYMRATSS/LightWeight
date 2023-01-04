package com.example.lightweight

import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.AdapterClass.ViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView

class AdapterClass(private val list: ArrayList<meal>, private val clickListener: ClickListener) : RecyclerView.Adapter<AdapterClass.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Modal = list[position]
        holder.id.text = list[position].id
        holder.desc.text = "Kalori: " +list[position].kalori + "\n" +  "Karbonhidrat: " + list[position].karbonhidrat +"\n"+
                "Yağ: " +list[position].yağ + "\n" + "Protein: " +list[position].protein + "\n"

        holder.enterGram.setOnClickListener {
            clickListener.ClickedItem(Modal)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.foodId)
        val desc: TextView = itemView.findViewById(R.id.descrb)
        val enterGram: Button = itemView.findViewById(R.id.enterGram)
    }

    interface ClickListener {
        fun ClickedItem(meal: meal)
    }
}
