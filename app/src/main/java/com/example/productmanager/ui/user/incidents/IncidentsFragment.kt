package com.example.productmanager.ui.user.incidents

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.productmanager.R
import com.example.productmanager.databinding.UserFragmentIncidentsBinding
import com.example.productmanager.ui.user.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncidentsFragment : Fragment() {

    private var _binding: UserFragmentIncidentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val incidentsBinding: IncidentsViewModel by viewModels()
    private var email = ""
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserFragmentIncidentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initObservers()
        initListeners()
        return root
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener {
            if (checkAndUpdateCheckTextInput()) {
                incidentsBinding.onCreateIncident(
                    email,
                    binding.etBarcode.text.toString(),
                    binding.etDescription.text.toString()
                )
            }

        }
    }

    private fun showSucces() {
        Toast.makeText(activity, R.string.succes, Toast.LENGTH_SHORT).show()
        binding.etBarcode.setText("")
        binding.etDescription.setText("")
    }

    private fun initObservers() {
        incidentsBinding.createIncident.observe(viewLifecycleOwner) {
            if (it) {
                showSucces()
            }else{
                showError()
            }
        }

        userDataViewModel.userMail.observe(viewLifecycleOwner) {
            email = it
        }
    }

    private fun showError() {
        Toast.makeText(activity, R.string.db_save_error, Toast.LENGTH_SHORT).show()
        binding.etBarcode.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkAndUpdateCheckTextInput(): Boolean {
        var _isEmpty = false
        val til_barcode = binding.tilNameOrBarcode
        val et_barcode = binding.etBarcode.text.toString().trim()

        val til_description = binding.tilDescription
        val et_description = binding.etDescription.text.toString().trim()
        val dictionary = mapOf(
            til_barcode to et_barcode,
            til_description to et_description
        )

        for ((til, et) in dictionary) {
            _isEmpty = et.isEmpty()
            if (_isEmpty) {
                til.helperText = getText(R.string.empty_box)
                til.setHelperTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red_error
                        )
                    )
                )
            } else {

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
}