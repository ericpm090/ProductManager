package com.example.productmanager.ui.admin.ui.projects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.productmanager.databinding.FragmentProjectsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectsFragment : Fragment() {

    private var _binding: FragmentProjectsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val homeAdminViewModel: ProjectsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // val homeViewModel =
        //      ViewModelProvider(this).get(ProjectsViewModel::class.java)

        _binding = FragmentProjectsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        /* homeAdminViewModel.onStartAdminFragment.observe(viewLifecycleOwner){
             data -> binding.txtWelcome.text = data.toString()
         }*/

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}