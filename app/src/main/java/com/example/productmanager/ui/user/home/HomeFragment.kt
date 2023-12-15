package com.example.productmanager.ui.user.home

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.productmanager.R
import com.example.productmanager.databinding.UserFragmentHomeBinding
import com.example.productmanager.domain.model.entities.Tool
import com.example.productmanager.ui.login.LoginActivity
import com.example.productmanager.ui.user.UserDataViewModel
import com.example.productmanager.ui.user.tool_screen.ToolScreenActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: UserFragmentHomeBinding? = null
    private val homeFragmentViewModel: HomeViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            binding.etBarcode.text = result.contents
            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Scanned: ${result.contents}", Toast.LENGTH_LONG).show()
        }
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserFragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initListeners()
        initObservers()
        return root
    }


    private fun initObservers() {
        homeFragmentViewModel.searchTool.observe(viewLifecycleOwner) {
            if (it != null) {
                showTool(it)
            } else {
                showError()
            }
        }


        userDataViewModel.userMail.observe(viewLifecycleOwner) { usr_email ->
            binding.txtUserEmail.text = "Welcome " + usr_email
            homeFragmentViewModel.getPendingTools(usr_email)

        }

        homeFragmentViewModel.pendingTools.observe(viewLifecycleOwner) { nPendingTools ->
            binding.txtPendingTools.text =
                getString(R.string.txt_pendingTools) + " " + nPendingTools.toString()
        }

        homeFragmentViewModel.totalRentalTools.observe(viewLifecycleOwner) { nRentalsTools ->
            binding.txtTotalToolsRented.text =
                getString(R.string.txt_totalRentalTools) + " " + nRentalsTools.toString()
        }

        homeFragmentViewModel.totalIncidences.observe(viewLifecycleOwner) { nIncidences ->
            binding.txtIncidencesCreated.text =
                getString(R.string.txt_incidencesCreated) + " " + nIncidences.toString()
        }

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

    private fun initListeners() {
        binding.btnOpentool.setOnClickListener {
            homeFragmentViewModel.onBarcodeRead(binding.etBarcode.text.toString())
        }

        binding.btnLogout.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        binding.floatingBtnScanner.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())

        }
    }

    private fun showTool(tool: Tool) {
        Log.i("HomeFragment", "Tool finded! " + tool.name)
        val intent = Intent(context, ToolScreenActivity::class.java).apply {
            putExtra("photo", tool.photo)
            putExtra("name", tool.name)
            putExtra("barcode", tool.barcode)
            putExtra("project", tool.project)
            putExtra("status", tool.status)


        }
        startActivity(intent)
    }

    /*  private fun initWelcome() {
              val viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
              val txt = viewModel.welcome_text
              binding.txtWelcome.text = txt
              //Log.i("HomeFragment", "email recived "+text)

      }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}