package com.example.productmanager.ui.admin.ui.tools

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.productmanager.R
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


        initObservers()
        initListeners()
        spinnerListener()


        return root
    }


    private fun initObservers() {
        toolFragmentViewModel.addTool.observe(viewLifecycleOwner) {
            if (it) showSucces()
            else showError()
        }
        toolFragmentViewModel.searchTool.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.etName.setText(it.name)
                binding.etDesription.setText(it.description)
                //binding.etProject.setText(it.project)
            } else {
                showError()
            }
        }

        toolFragmentViewModel.deleteTool.observe(viewLifecycleOwner) {
            if (it) showSucces()
            else showError()
        }

        toolFragmentViewModel.projectList.observe(viewLifecycleOwner) {
            if (it != null) {
                initSpinner(it)

            }
        }


    }

    private fun initSpinner(list: MutableList<String>) {

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        binding.spinnerProjects.adapter = adapter

    }


    private fun initListeners() {


        binding.btnSave.setOnClickListener {
            toolFragmentViewModel.onAddToolSelected(
                binding.etName.text.toString(),
                spinnerListener().toString(),

                // binding.etProject.text.toString(),
                binding.etDesription.text.toString()
            )
        }

        binding.btnSearch.setOnClickListener {
            toolFragmentViewModel.onSearchToolSelected(binding.etScan.text.toString())
        }

        binding.btnRemove.setOnClickListener {
            toolFragmentViewModel.onDeleteToolSelected(binding.etName.text.toString())
        }

        //listener spinner call by default
        toolFragmentViewModel.getProjects()


    }

    private fun spinnerListener(): String? {

        var selectedItem: String? = null

        binding.spinnerProjects.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //selectedItem =toolFragmentViewModel.list[position]
                    selectedItem = parent?.getItemAtPosition(position).toString()
                    // Do something with the selected item
                    // For example, you can log it or update a variable
                    println("Selected Item: ${selectedItem}")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do something when nothing is selected (optional)
                }
            }
        return selectedItem
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        Toast.makeText(activity, R.string.succes, Toast.LENGTH_SHORT).show()
    }
}