package com.example.productmanager.ui.admin.ui.incidences

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productmanager.databinding.AdminFragmentIncidencesBinding
import com.example.productmanager.ui.admin.ui.incidences.adapter.IncidencesAdapter

class IncidencesFragment : Fragment() {

    private var _binding: AdminFragmentIncidencesBinding? = null

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

        return root
    }


    private fun initRecyclerView() {

        Log.d("IncidencesFragment", "Number of items: ${IncidencesProvider.list.size}")
        binding.recyclerIncidences.layoutManager = LinearLayoutManager(context)
        binding.recyclerIncidences.adapter = IncidencesAdapter(IncidencesProvider.list)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}