package com.example.productmanager.ui.admin.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.productmanager.databinding.FragmentLocationsBinding

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(LocationsViewModel::class.java)

        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /* val textView: TextView = binding.textNotifications
         notificationsViewModel.text.observe(viewLifecycleOwner) {
             textView.text = it
         }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}