package com.example.productmanager.ui.user.rental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productmanager.R
import com.example.productmanager.domain.model.entities.Tool

class RentalsAdapter(private val rentalList: MutableList<Tool>):
    RecyclerView.Adapter<RentalsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalsViewHolder {
         val inflater = LayoutInflater.from(parent.context)
        return RentalsViewHolder(inflater.inflate(R.layout.recyclerview_tool_sample, parent, false))
    }

    override fun getItemCount(): Int {
        return rentalList.size
    }

    override fun onBindViewHolder(holder: RentalsViewHolder, position: Int) {
        holder.render(rentalList[position])
    }
}