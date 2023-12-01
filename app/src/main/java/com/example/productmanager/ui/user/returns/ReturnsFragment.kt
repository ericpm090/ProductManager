package com.example.productmanager.ui.user.returns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.productmanager.databinding.UserFragmentReturnsBinding
import com.example.productmanager.ui.user.UserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReturnsFragment : Fragment() {

    private var _binding: UserFragmentReturnsBinding? = null
    private val returnFragmentViewModel: ReturnsViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserFragmentReturnsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initUser()
        initListeners()

        return root
    }

    private fun initUser() {
        val activity = requireActivity() as? UserActivity
        returnFragmentViewModel.email = activity?.getUser().toString()
    }

    private fun initListeners() {
        binding.btnAddTool.setOnClickListener {
            returnFragmentViewModel.onBarcodeRead(binding.etBarcode.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}