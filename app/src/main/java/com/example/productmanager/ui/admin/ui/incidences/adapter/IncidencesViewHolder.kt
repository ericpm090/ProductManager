package com.example.productmanager.ui.admin.ui.incidences.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.productmanager.databinding.RecyclerviewAdminIncidenceSampleBinding
import com.example.productmanager.domain.model.Incidence

class IncidencesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = RecyclerviewAdminIncidenceSampleBinding.bind(view)

    fun render(incidenceModel: Incidence) {
        binding.recyclerviewUser.text = incidenceModel.employee
        binding.recyclerviewTool.text = incidenceModel.tool
        binding.recyclerviewDate.text = incidenceModel.date
        binding.recyclerviewDescription.text = incidenceModel.description
        //agregar foto con glide
        //Glide.with(photo.context).load(incidenceModel.photo).into(photo)
    }


}