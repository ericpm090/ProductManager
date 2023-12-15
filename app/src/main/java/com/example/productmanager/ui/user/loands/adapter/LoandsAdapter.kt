package com.example.productmanager.ui.user.loands.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productmanager.R
import com.example.productmanager.domain.model.entities.RentalTool

class LoandsAdapter(private val rentalList: List<RentalTool>):
    RecyclerView.Adapter<LoandsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoandsViewHolder {
         val inflater = LayoutInflater.from(parent.context)
        return LoandsViewHolder(inflater.inflate(R.layout.recyclerview_loands_sample, parent, false))
    }

    override fun getItemCount(): Int {
        return rentalList.size
    }

    override fun onBindViewHolder(holder: LoandsViewHolder, position: Int) {
        holder.render(rentalList[position])
    }
}