package com.example.rescuehub.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rescuehub.R
import com.example.rescuehub.data.local.RescueCase
import com.example.rescuehub.ui.map.MapsActivity

class RescueCaseAdapter : ListAdapter<RescueCase, RescueCaseAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvRange: TextView = itemView.findViewById(R.id.tv_range)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        val btnCall: TextView = itemView.findViewById(R.id.btn_call)
        val btnResponse: TextView = itemView.findViewById(R.id.btn_response)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.response_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val case = getItem(position)
        holder.apply {
            tvName.text = case.name
            tvDate.text = case.date
            tvRange.text = case.latitude.toString()
            tvStatus.text = case.status
        }

        holder.btnResponse.setOnClickListener {
            val intent = Intent(holder.itemView.context, MapsActivity::class.java)
            intent.putExtra(MapsActivity.EXTRA_LATITUDE, case.latitude)
            intent.putExtra(MapsActivity.EXTRA_LONGITUDE, case.longitude)
            holder.itemView.context.startActivity(intent)
        }
    
        holder.btnCall.setOnClickListener {
            holder.itemView.context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${case.phone}")))
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RescueCase>() {
            override fun areItemsTheSame(oldItem: RescueCase, newItem: RescueCase): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RescueCase, newItem: RescueCase): Boolean {
                return oldItem == newItem
            }
        }
    }

}