package com.example.productmanager.ui.admin.ui.users

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
import com.example.productmanager.databinding.AdminFragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var _binding: AdminFragmentUsersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val userFragmentViewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = AdminFragmentUsersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //val viewmodel = ViewModelProvider(this).get(UsersViewModel::class.java)

        initListeners()
        initObservers()

        return root
    }


    private fun initListeners() {

        binding.btnSearch.setOnClickListener {
            userFragmentViewModel.onSearchSelected(binding.editTextEmail.text.toString())
            //userFragmentViewModel.onSearchSelected("eric.pm090@gmail.com") //remove after testing

        }

        binding.btnSave.setOnClickListener {
            userFragmentViewModel.onModifySelected(
                binding.etMail.text.toString(),
                binding.etName.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.btnRemove.setOnClickListener {
            userFragmentViewModel.onRemoveSelected(binding.etMail.text.toString())
        }
    }

    private fun initObservers() {


        userFragmentViewModel.searchUser.observe(viewLifecycleOwner) { res ->
            if (res != null) {
                binding.etName.setText(res.name)
                binding.etMail.setText(res.email)
                binding.etPassword.setText(res.password)
            }
        }

        userFragmentViewModel.modifyUser.observe(viewLifecycleOwner) { res ->
            if (res != null) showSucces()
            else showError()
        }


        userFragmentViewModel.deleteUser.observe(viewLifecycleOwner) { res ->
            if (res) showSucces()
            else showError()
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

    private fun showSucces() {
        Toast.makeText(activity, R.string.succes, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}