package com.example.productmanager.ui.admin.ui.incidences.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.productmanager.R
import com.example.productmanager.databinding.RecyclerviewHistorytoolSampleBinding
import com.example.productmanager.domain.model.entities.RentalTool
import com.example.productmanager.domain.model.entities.RentalToolStatus

class HistoryToolsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = RecyclerviewHistorytoolSampleBinding.bind(view)
    val context = view.context
    fun render(
        rentalTool: RentalTool
    ) {
        binding.recyclerviewUser.text = rentalTool.userMail
        binding.recyclerviewPickUpdate.text = rentalTool.pickUpDate
        binding.recyclerviewDropOffdate.text = rentalTool.dropOffDate
        binding.recyclerviewStatus.text = rentalTool.status
        val textColorRes = when (rentalTool.status) {
            RentalToolStatus.PENDING.toString() -> R.color.red
            else -> R.color.green
        }

        binding.recyclerviewStatus.setTextColor(ContextCompat.getColor(context, textColorRes))


    }


}