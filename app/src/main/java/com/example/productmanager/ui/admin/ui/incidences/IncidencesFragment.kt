package com.example.productmanager.ui.admin.ui.incidences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productmanager.R
import com.example.productmanager.databinding.AdminFragmentIncidencesBinding
import com.example.productmanager.domain.model.entities.Incidence
import com.example.productmanager.ui.admin.ui.incidences.adapter.IncidencesAdapter
import com.example.productmanager.ui.admin.ui.incidences.adapter.IncidencesProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncidencesFragment : Fragment() {

    private var _binding: AdminFragmentIncidencesBinding? = null
    private val incidencesFragmentViewModel: IncidencesViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = AdminFragmentIncidencesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initRecyclerView()
        initObservers()
        initListeners()
        return root
    }

    private fun initListeners() {
        binding.btnUpdate.setOnClickListener {
            incidencesFragmentViewModel.onUpdateSelected()
        }
    }

    private fun initObservers() {

        incidencesFragmentViewModel.updateIncidents.observe(viewLifecycleOwner) {
            if (it != null) {
                updateRecyclerView(it)
            } else {
                showNothingtoUpdate()
            }
        }


    }

    private fun showNothingtoUpdate() {
        Toast.makeText(activity, R.string.nothing_to_update, Toast.LENGTH_SHORT).show()
    }


    private fun initRecyclerView() {

        binding.rvIncidences.layoutManager = LinearLayoutManager(context)
        binding.rvIncidences.adapter = IncidencesAdapter(
            IncidencesProvider.incidencesList,
            { incidence -> onIncidenceSelected(incidence) },
            onClickDelete = { position -> onItemDelete(position) }
        )

    }

    private fun onIncidenceSelected(incidence: Incidence) {
        Toast.makeText(activity, "Incidence ${incidence.id} selected", Toast.LENGTH_SHORT).show()

        //Borrar items
        //https://www.youtube.com/watch?v=6O_goNNuXWk&t=614s
    }

    private fun onItemDelete(position: Int) {
        incidencesFragmentViewModel.onDeleteItem(IncidencesProvider.incidencesList.get(position))
        incidencesFragmentViewModel.solveInvident.observe(viewLifecycleOwner){
            if(it == true){
                IncidencesProvider.incidencesList.removeAt(position)
                binding.rvIncidences.adapter?.notifyItemRemoved(position)
            }
        }

    }

    fun updateRecyclerView(incidences: MutableList<Incidence>) {

        val previousSize = IncidencesProvider.incidencesList.size
        // Update the data source
        IncidencesProvider.incidencesList.clear()
        IncidencesProvider.incidencesList.addAll(incidences)
        // Notify the adapter about the range of inserted items
        binding.rvIncidences.adapter?.notifyItemRangeInserted(previousSize, incidences.size)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}