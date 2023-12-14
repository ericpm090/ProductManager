package com.example.productmanager.ui.user.rental

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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productmanager.R
import com.example.productmanager.databinding.UserFragmentRentalBinding
import com.example.productmanager.domain.model.entities.Tool
import com.example.productmanager.ui.user.UserDataViewModel
import com.example.productmanager.ui.user.rental.adapter.RentalsAdapter
import com.example.productmanager.ui.user.rental.adapter.RentalsProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentalFragment : Fragment() {

    private var _binding: UserFragmentRentalBinding? = null
    private val rentalViewModel: RentalViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var email = ""
    private var spinnerLocationValue: String = ""
    private var spinnerLocationsList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserFragmentRentalBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initListeners()
        initObservers()
        initRecyclerView()
        spinnerListener()
        return root
    }

    private fun spinnerListener() {
        binding.spinnerLocations.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    spinnerLocationValue = parent?.getItemAtPosition(position).toString()
                    Log.i("ToolFragment", "Selected location" + spinnerLocationValue)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do something when nothing is selected (optional)
                }
            }
    }

    private fun initLocationSpinner(list: MutableList<String>, value: String) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        binding.spinnerLocations.adapter = adapter
        val position = list.indexOf(value)
        binding.spinnerLocations.setSelection(position)

    }

    fun initRecyclerView() {
        binding.rvListRentals.layoutManager = LinearLayoutManager(context)
        RentalsProvider.rentalList.clear()
        binding.rvListRentals.adapter = RentalsAdapter(RentalsProvider.rentalList)
    }

    private fun initObservers() {
        rentalViewModel.searchTool.observe(viewLifecycleOwner) {
            if (it != null) {
                rentalViewModel.rentalList.add(it)
                updateRecyclerView(it)
                binding.etBarcode.setText("")
            } else {
                showError()
            }
        }

        rentalViewModel.addRentalTool.observe(viewLifecycleOwner) {
            if (it == true) {
                initRecyclerView()
                showSucces()
            }
        }

        userDataViewModel.userMail.observe(viewLifecycleOwner) {
            email = it
        }

        rentalViewModel.locationList.observe(viewLifecycleOwner) {
            if (it != null) {
                spinnerLocationsList = it
                initLocationSpinner(it, it.get(0))

            }
        }


    }

    private fun showError() {
        binding.tilNameOrBarcode.boxBackgroundColor =
            ContextCompat.getColor(requireContext(), R.color.red_error)
        binding.tilNameOrBarcode.helperText = getText(R.string.db_not_found)
        binding.tilNameOrBarcode.setHelperTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red_error
                )
            )
        )
    }


    private fun initListeners() {
        binding.btnSearch.setOnClickListener {
            if(checkAndUpdateCheckTextInput()){
                rentalViewModel.onSearchSelected(binding.etBarcode.text.toString())
            }

        }

        binding.btnRegister.setOnClickListener {
            if(rentalViewModel.rentalList.isNotEmpty()){
                rentalViewModel.onRegisterSelected(email, spinnerLocationValue)
            }else{
                showError()
            }

        }
        rentalViewModel.getLocations()
    }

    fun updateRecyclerView(tool: Tool) {
        RentalsProvider.rentalList.add(tool)
        binding.rvListRentals.adapter?.notifyItemInserted(RentalsProvider.rentalList.size + 1)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSucces() {
        Toast.makeText(activity, R.string.succes, Toast.LENGTH_SHORT).show()
    }

    private fun checkAndUpdateCheckTextInput(): Boolean {

        val til_barcode = binding.tilNameOrBarcode
        val et_barcode = binding.etBarcode.text.toString().trim()

        val _isEmpty = et_barcode.isEmpty()
        if (_isEmpty) {
            til_barcode.helperText = getText(R.string.empty_box)
            til_barcode.setHelperTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red_error
                    )
                )
            )
        } else {

            til_barcode.helperText = ""
            til_barcode.setHelperTextColor(
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


}