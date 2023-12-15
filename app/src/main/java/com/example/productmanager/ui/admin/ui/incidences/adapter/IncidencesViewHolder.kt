package com.example.productmanager.ui.admin.ui.incidences.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.productmanager.databinding.RecyclerviewAdminIncidenceSampleBinding
import com.example.productmanager.domain.model.entities.Incidence

class IncidencesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = RecyclerviewAdminIncidenceSampleBinding.bind(view)

    fun render(
        incidence: Incidence,
        onClickListener: (Incidence) -> Unit,
        onClickDelete: (Int) -> Unit
    ) {
        binding.recyclerviewUser.text = incidence.employee
        binding.recyclerviewTool.text = incidence.tool_name
        binding.recyclerviewDate.text = incidence.date
        binding.recyclerviewBarcode.text = incidence.id_tool
        binding.recyclerviewDescription.text = incidence.description

        binding.recyclerviewCheckboxSolved.setOnClickListener {
                onClickListener(incidence)

        }
        binding.btnDeleteAndNotify.setOnClickListener {
            if(binding.recyclerviewCheckboxSolved.isChecked){
                onClickDelete(adapterPosition)
            }else{
                Toast.makeText(binding.recyclerviewBarcode.context, "Select solve first", Toast.LENGTH_SHORT).show()
            }

        }

    }


}