package com.example.productmanager.ui.user.home

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.productmanager.R
import com.example.productmanager.databinding.UserFragmentHomeBinding
import com.example.productmanager.domain.model.Tool
import com.example.productmanager.ui.user.tool_screen.ToolScreenActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: UserFragmentHomeBinding? = null
    private val homeFragmentViewModel: HomeViewModel by viewModels()

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
        //initWelcome()
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