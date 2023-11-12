package com.example.productmanager.ui.admin.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.productmanager.databinding.FragmentToolsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToolsFragment : Fragment() {

    private var _binding: FragmentToolsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val toolFragmentViewModel: ToolsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentToolsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initListeners()

        return root
    }

    private fun initListeners() {
        binding.btnCreate.setOnClickListener {

        }

        binding.btnSearch.setOnClickListener {

        }

        binding.btnRemove.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}