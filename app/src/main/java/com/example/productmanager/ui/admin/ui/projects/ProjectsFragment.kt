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
import com.example.productmanager.databinding.AdminFragmentProjectsBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectsFragment : Fragment() {

    private var _binding: AdminFragmentProjectsBinding? = null
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            binding.etScan.text = result.contents
            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Scanned: ${result.contents}", Toast.LENGTH_LONG).show()
        }
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val projectFragmentViewModel: ProjectsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // val homeViewModel =
        //      ViewModelProvider(this).get(ProjectsViewModel::class.java)

        _binding = AdminFragmentProjectsBinding.inflate(inflater, container, false)
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
            if (it == true) showSucces()
            else  showError()
        }
        projectFragmentViewModel.findProject.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.etName.setText(it.name)
            } else {
                showBarcodeError()
            }
        }

        projectFragmentViewModel.deleteProject.observe(viewLifecycleOwner) {
            if (it) showSucces()
            else showBarcodeError()
        }
    }


    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            if(checkAndUpdateCheckTextInput()){
                projectFragmentViewModel.onAddProjectSelected(
                    binding.etName.text.toString()

                )
            }

        }

        binding.btnSearch.setOnClickListener {
            projectFragmentViewModel.onSearchProjectSelected(binding.etScan.text.toString())
        }

        binding.btnRemove.setOnClickListener {
            projectFragmentViewModel.onDeleteProjectSelected(binding.etScan.text.toString())
        }

        binding.floatingBtnScanner.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())


        }
    }

    private fun checkAndUpdateCheckTextInput(): Boolean {

        val til_name = binding.tilName
        val et_name = binding.etName.text.toString()
        val _isEmpty = et_name.isEmpty()
        if(_isEmpty){
            til_name.helperText = getText(R.string.empty_box)
            til_name.setHelperTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red_error
                    )
                )
            )
        }else{

            til_name.helperText = ""
            til_name.setHelperTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                )
            }


        return !_isEmpty

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showError() {
        binding.tilName.helperText = getString(R.string.db_save_error)
        binding.tilName.setHelperTextColor(ColorStateList.valueOf(Color.RED))
    }

    private fun showBarcodeError() {
        binding.tilNameOrBarcode.boxBackgroundColor =
            ContextCompat.getColor(requireContext(), R.color.red_error)
        binding.tilNameOrBarcode.helperText = getText(R.string.db_searchOrdelete_error)
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