package com.example.productmanager.ui.admin.ui.incidences.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productmanager.R
import com.example.productmanager.domain.model.entities.Incidence


class IncidencesAdapter(
    private val incidencesList: List<Incidence>,
    private val onClickListener: (Incidence) -> Unit,
    private val onClickDelete: (Int) -> Unit
) :
    RecyclerView.Adapter<IncidencesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncidencesViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return IncidencesViewHolder(
            inflater.inflate(
                R.layout.recyclerview_admin_incidence_sample,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: IncidencesViewHolder, position: Int) {
        Log.d("IncidencesAdapter", "Binding item at position $position")

        holder.render(incidencesList[position], onClickListener, onClickDelete)


    }

    override fun getItemCount(): Int {
        return incidencesList.size
    }

}