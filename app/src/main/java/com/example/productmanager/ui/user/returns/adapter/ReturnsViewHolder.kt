package com.example.productmanager.ui.user.returns.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productmanager.databinding.RecyclerviewLoandsSampleBinding
import com.example.productmanager.domain.model.entities.RentalTool

class ReturnsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = RecyclerviewLoandsSampleBinding.bind(view)

    fun render(rentalTool: RentalTool) {
        Glide.with(binding.imgTool.context).load(rentalTool.photo).into(binding.imgTool)
        binding.recyclerviewProject.text = rentalTool.project
        binding.recyclerviewTool.text = rentalTool.toolName
        binding.recyclerviewStatus.text = rentalTool.status
        binding.recyclerviewStartDate.text = rentalTool.pickUpDate
    }
}