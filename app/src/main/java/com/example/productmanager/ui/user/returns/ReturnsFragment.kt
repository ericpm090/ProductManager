package com.example.productmanager.ui.user.returns

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productmanager.R
import com.example.productmanager.databinding.UserFragmentReturnsBinding
import com.example.productmanager.domain.model.entities.RentalTool
import com.example.productmanager.ui.user.UserDataViewModel
import com.example.productmanager.ui.user.returns.adapter.ReturnsAdapter
import com.example.productmanager.ui.user.returns.adapter.ReturnsProvider
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReturnsFragment : Fragment() {

    private var _binding: UserFragmentReturnsBinding? = null
    private val returnFragmentViewModel: ReturnsViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private var email = ""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            binding.etBarcode.text = result.contents
            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Scanned: ${result.contents}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserFragmentReturnsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initObservers()
        initListeners()
        initRecyclerView()

        return root
    }

    private fun initObservers() {

        returnFragmentViewModel.searchPendingTool.observe(viewLifecycleOwner){
            if(it!=null){
                returnFragmentViewModel.rentalList.add(it)
                updateRecyclerView(it)
                binding.etBarcode.setText("")
            }else{
                showError()
            }
        }

        returnFragmentViewModel.registerRentalTools.observe(viewLifecycleOwner){
            clearRecyclerView()
        }


        userDataViewModel.userMail.observe(viewLifecycleOwner) {
            email = it
        }


    }

    private fun clearRecyclerView() {
        ReturnsProvider.rentalList.clear()
        binding.rvIncidences.adapter?.notifyDataSetChanged()
    }

    private fun initListeners() {
        binding.btnSearch.setOnClickListener {
            if(checkAndUpdateCheckTextInput()) returnFragmentViewModel.onBarcodeRead(email, binding.etBarcode.text.toString())
        }
        binding.btnRegister.setOnClickListener {
            if(returnFragmentViewModel.rentalList.isNotEmpty()){
                returnFragmentViewModel.onRegisterSelected()
            }else{
                showError()
            }
        }

        binding.floatingBtnScanner.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())

        }
    }
    fun updateRecyclerView(rentalTool: RentalTool) {
        ReturnsProvider.rentalList.add(rentalTool)
        binding.rvIncidences.adapter?.notifyItemInserted(ReturnsProvider.rentalList.size + 1)

    }
    fun initRecyclerView() {
        binding.rvIncidences.layoutManager = LinearLayoutManager(context)
        ReturnsProvider.rentalList.clear()
        binding.rvIncidences.adapter = ReturnsAdapter(ReturnsProvider.rentalList)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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