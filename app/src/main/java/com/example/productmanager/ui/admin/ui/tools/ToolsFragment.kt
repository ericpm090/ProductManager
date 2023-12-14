package com.example.productmanager.ui.admin.ui.tools

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
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
import com.example.productmanager.databinding.AdminFragmentToolsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToolsFragment : Fragment() {

    private var _binding: AdminFragmentToolsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val toolFragmentViewModel: ToolsViewModel by viewModels()
    private var spinnerProjectsValue:String = ""
    private var spinnerTypeValue:String = ""
    private var spinnerLocationValue:String = ""
    private var spinnerProjectsList = mutableListOf<String>()
    private var spinnerTypesList = mutableListOf<String>()
    private var spinnerLocationsList = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = AdminFragmentToolsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initObservers()
        initListeners()
        spinnerListener()


        return root
    }


    private fun initObservers() {
        toolFragmentViewModel.addTool.observe(viewLifecycleOwner) {
            if (it == true) showSucces()
            else showError()
        }
        toolFragmentViewModel.searchTool.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.etName.setText(it.name)

                initProjectSpinner(spinnerProjectsList, it.project)
                initLocationSpinner(spinnerLocationsList, it.location)
                initTypeSpinner(spinnerTypesList, it.type)



                //binding.etProject.setText(it.project)
            } else {
                showBarcodeError()
            }
        }

        toolFragmentViewModel.deleteTool.observe(viewLifecycleOwner) {
            if (it) showSucces()
            else showBarcodeError()
        }

        toolFragmentViewModel.projectList.observe(viewLifecycleOwner) {
            if (it != null) {
                spinnerProjectsList = it
                initProjectSpinner(it, it.get(0))

            }
        }

        toolFragmentViewModel.locationList.observe(viewLifecycleOwner) {
            if (it != null) {
                spinnerLocationsList = it
                initLocationSpinner(it, it.get(0))

            }
        }

        toolFragmentViewModel.typeList.observe(viewLifecycleOwner) {
            if (it != null) {
                spinnerTypesList = it
                initTypeSpinner(it, it.get(0))

            }
        }


    }
    private fun initListeners() {


        binding.btnSave.setOnClickListener {
            if(checkAndUpdateCheckTextInput()){
                toolFragmentViewModel.onAddToolSelected(
                    binding.etName.text.toString(),
                    spinnerProjectsValue,
                    spinnerLocationValue,
                    spinnerTypeValue,
                    binding.etPhoto.text.toString()

                )
            }

        }

        binding.btnSearch.setOnClickListener {
            toolFragmentViewModel.onSearchToolSelected(binding.etScan.text.toString())
        }

        binding.btnRemove.setOnClickListener {
            if(checkAndUpdateCheckTextInput()) {
                toolFragmentViewModel.onDeleteToolSelected(binding.etName.text.toString())
            }
        }

        //listener spinner call by default
        toolFragmentViewModel.getProjects()
        toolFragmentViewModel.getLocations()
        toolFragmentViewModel.getTypes()


    }
    private fun initTypeSpinner(list: MutableList<String>, value:String) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        binding.spinnerType.adapter = adapter
        val position = list.indexOf(value)
        binding.spinnerType.setSelection(position)

    }

    private fun initLocationSpinner(list: MutableList<String>, value:String) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        binding.spinnerLocations.adapter = adapter
        val position = list.indexOf(value)
        binding.spinnerLocations.setSelection(position)

    }


    private fun initProjectSpinner(list: MutableList<String>, value:String) {


        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        binding.spinnerProjects.adapter = adapter
        val position = list.indexOf(value)
        binding.spinnerProjects.setSelection(position)
    }


    private fun checkAndUpdateCheckTextInput(): Boolean {
        var _isEmpty = false
        val til_name = binding.tilName
        val til_photo = binding.tilPhoto
        val et_name = binding.etName.text.toString().trim()
        val et_photo = binding.etPhoto.text.toString().trim()

        val dictionary = mapOf(
            til_name to et_name,
            til_photo to et_photo
        )

        for((til,et) in dictionary){
            _isEmpty = et.isEmpty()
            if(_isEmpty){
                til.helperText = getText(R.string.empty_box)
                til.setHelperTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red_error
                        )
                    )
                )
            }else{

                til.helperText = ""
                til.setHelperTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                )
            }
        }

        return !_isEmpty

    }



    private fun spinnerListener() {



        binding.spinnerProjects.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    spinnerProjectsValue = parent?.getItemAtPosition(position).toString()
                    Log.i("ToolFragment", "Selected project "+ spinnerProjectsValue)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do something when nothing is selected (optional)
                }
            }

        binding.spinnerType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    spinnerTypeValue = parent?.getItemAtPosition(position).toString()
                    Log.i("ToolFragment", "Selected type"+ spinnerTypeValue)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do something when nothing is selected (optional)
                }
            }

        binding.spinnerLocations.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    spinnerLocationValue = parent?.getItemAtPosition(position).toString()
                    Log.i("ToolFragment", "Selected location"+ spinnerLocationValue)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do something when nothing is selected (optional)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun showError() {
        binding.tilName.boxBackgroundColor =
            ContextCompat.getColor(requireContext(), R.color.red_error)
        binding.tilName.helperText = getText(R.string.db_save_error)
        binding.tilName.setHelperTextColor(
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