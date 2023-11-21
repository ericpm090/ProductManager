package com.example.productmanager.ui.admin.ui.projects

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.productmanager.R
import com.example.productmanager.databinding.FragmentProjectsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectsFragment : Fragment() {

    private var _binding: FragmentProjectsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val projectFragmentViewModel: ProjectsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // val homeViewModel =
        //      ViewModelProvider(this).get(ProjectsViewModel::class.java)

        _binding = FragmentProjectsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initListeners()
        initObservers()


        /* homeAdminViewModel.onStartAdminFragment.observe(viewLifecycleOwner){
             data -> binding.txtWelcome.text = data.toString()
         }*/

        return root
    }

    private fun initObservers() {
        projectFragmentViewModel.addProject.observe(viewLifecycleOwner) {
            if (it) showSucces()
            else showBarcodeError()
        }
        projectFragmentViewModel.findProject.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.etName.setText(it)
            } else {
                showError()
            }
        }

        projectFragmentViewModel.deleteProject.observe(viewLifecycleOwner) {
            if (it) showSucces()
            else showError()
        }
    }


    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            projectFragmentViewModel.onAddProjectSelected(
                binding.etScan.text.toString()

            )
        }

        binding.btnSearch.setOnClickListener {
            projectFragmentViewModel.onSearchProjectSelected(binding.etScan.text.toString())
        }

        binding.btnRemove.setOnClickListener {
            projectFragmentViewModel.onDeleteProjectSelected(binding.etScan.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showBarcodeError() {
        binding.tilNameOrBarcode.helperText = getString(R.string.db_save_error)
        binding.tilNameOrBarcode.setHelperTextColor(ColorStateList.valueOf(Color.RED))
    }

    private fun showError() {
        binding.tilNameOrBarcode.boxBackgroundColor =
            ContextCompat.getColor(requireContext(), R.color.red_error)
        binding.tilNameOrBarcode.helperText = getText(R.string.db_save_error)
        binding.tilNameOrBarcode.setHelperTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red_error
                )
            )
        )


    }


    private fun showSucces() {
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.setText(R.string.db_project_search_delete_succes)
        toast.show()

        // binding.tilName.helperText = getString(R.string.db_project_search_delete_succes)
        //binding.tilName.setHelperTextColor(ColorStateList.valueOf(Color.GREEN))
    }
}