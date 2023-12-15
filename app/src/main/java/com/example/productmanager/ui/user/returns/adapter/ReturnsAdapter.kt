package com.example.productmanager.ui.user.returns.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productmanager.R
import com.example.productmanager.domain.model.entities.RentalTool

class ReturnsAdapter (private val rentalList: MutableList<RentalTool>):
RecyclerView.Adapter<ReturnsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReturnsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReturnsViewHolder(inflater.inflate(R.layout.recyclerview_loands_sample, parent, false))
    }

    override fun getItemCount(): Int {
        return rentalList.size
    }

    override fun onBindViewHolder(holder: ReturnsViewHolder, position: Int) {
        holder.render(rentalList[position])
    }
}