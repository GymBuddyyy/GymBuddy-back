package com.example.gymbuddy_back

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GymAdapter(private val gyms: MutableList<Gym>) : RecyclerView.Adapter<GymAdapter.GymViewHolder>() {

    class GymViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gym, parent, false)
        return GymViewHolder(view)
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        val gym = gyms[position]
        holder.tvName.text = gym.name
        holder.tvAddress.text = gym.address
    }

    override fun getItemCount() = gyms.size

    // 데이터 업데이트 메서드
    fun updateData(newGyms: List<Gym>) {
        gyms.clear()
        gyms.addAll(newGyms)
        notifyDataSetChanged()
    }
}
