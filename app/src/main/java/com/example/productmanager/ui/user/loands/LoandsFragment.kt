package com.example.productmanager.ui.user.loands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.productmanager.databinding.UserFragmentLoandsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoandsFragment : Fragment() {

    private var _binding: UserFragmentLoandsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserFragmentLoandsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}