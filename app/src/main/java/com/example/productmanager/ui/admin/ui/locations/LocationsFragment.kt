package com.example.productmanager.ui.admin.ui.locations

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.productmanager.R
import com.example.productmanager.databinding.AdminFragmentLocationsBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : Fragment() {

    private var _binding: AdminFragmentLocationsBinding? = null
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

    private val locationFragmentViewModel: LocationsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = AdminFragmentLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initListeners()
        initObservers()

        return root
    }

    private fun initObservers() {
        locationFragmentViewModel.addLocation.observe(viewLifecycleOwner) {
            if (it == true) showSucces()
            else showBarcodeError()
        }
        locationFragmentViewModel.findLocation.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.etName.setText(it.name)

            } else {
                showError()
            }
        }

        locationFragmentViewModel.deleteLocation.observe(viewLifecycleOwner) {
            if (it) showSucces()
            else showError()
        }
    }


    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            if(checkAndUpdateCheckTextInput()){
                locationFragmentViewModel.onAddLocationSelected(
                    binding.etName.text.toString()
                )
            }

        }

        binding.btnSearch.setOnClickListener {
            locationFragmentViewModel.onSearchLocationSelected(binding.etName.text.toString())
        }

        binding.btnRemove.setOnClickListener {
            locationFragmentViewModel.onDeleteLocationSelected(binding.etName.text.toString())
        }

        binding.floatingBtnScanner.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())


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