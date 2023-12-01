package com.example.productmanager.ui.user.rental

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.productmanager.databinding.UserFragmentRentalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentalFragment : Fragment() {

    private var _binding: UserFragmentRentalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserFragmentRentalBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}