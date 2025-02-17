package com.example.gymbuddy_back

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GymAdapter(
    private val gyms: MutableList<Gym>,
    private val onItemClick: (Gym) -> Unit // 체육관 선택 시 동작할 콜백 추가
) : RecyclerView.Adapter<GymAdapter.GymViewHolder>() {

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

        // 아이템 클릭 시 선택된 체육관 정보 전달
        holder.itemView.setOnClickListener {
            onItemClick(gym) // 선택한 체육관 데이터를 액티비티로 전달
        }
    }

    override fun getItemCount() = gyms.size

    // 🔄 검색 결과 업데이트 메서드 (RecyclerView 업데이트)
    fun updateData(newGyms: List<Gym>) {
        gyms.clear()
        gyms.addAll(newGyms)
        notifyDataSetChanged()
    }
}
