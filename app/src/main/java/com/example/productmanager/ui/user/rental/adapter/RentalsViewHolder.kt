package com.example.productmanager.ui.user.rental.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productmanager.databinding.RecyclerviewToolSampleBinding
import com.example.productmanager.domain.model.entities.Tool

class RentalsViewHolder(view: View):RecyclerView.ViewHolder(view) {

    val binding = RecyclerviewToolSampleBinding.bind(view)

    fun render(tool: Tool){
        Glide.with(binding.imgTool.context).load(tool.photo).into(binding.imgTool)
        binding.recyclerviewProject.text = tool.project
        binding.recyclerviewTool.text = tool.name
        binding.recyclerviewStatus.text = tool.status
    }
}