package com.example.productmanager.ui.admin.ui.incidences.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productmanager.R
import com.example.productmanager.domain.model.entities.RentalTool


class HistoryToolAdapter(
    private val rentalToolList: List<RentalTool>
) :
    RecyclerView.Adapter<HistoryToolsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryToolsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return HistoryToolsViewHolder(
            inflater.inflate(
                R.layout.recyclerview_historytool_sample,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: HistoryToolsViewHolder, position: Int) {
        Log.d("IncidencesAdapter", "Binding item at position $position")

        holder.render(rentalToolList[position])


    }

    override fun getItemCount(): Int {
        return rentalToolList.size
    }

}