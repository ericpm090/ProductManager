package com.example.productmanager.ui.admin.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.productmanager.R
import com.example.productmanager.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val userFragmentViewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUsersBinding.inflate(inflater, container, false)
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

        binding.btnModify.setOnClickListener {
            userFragmentViewModel.onModifySelected(
                binding.etUserMail.text.toString(),
                binding.etUserName.text.toString(),
                binding.etUserPassword.text.toString()
            )
        }

        binding.btnRemove.setOnClickListener {
            userFragmentViewModel.onRemoveSelected(binding.etUserMail.text.toString())
        }
    }

    private fun initObservers() {


        userFragmentViewModel.searchUser.observe(viewLifecycleOwner) { res ->
            if (res != null) {
                binding.etUserName.setText(res.name);
                binding.etUserMail.setText(res.email);
                binding.etUserPassword.setText(res.password)
            }
        }

        userFragmentViewModel.modifyUser.observe(viewLifecycleOwner) { res ->
            if (res != null) showSucces()
            else showAlert()
        }


        userFragmentViewModel.deleteUser.observe(viewLifecycleOwner) { res ->
            if (res) showSucces()
            else showAlert()
        }


    }

    private fun showAlert() {
        Toast.makeText(activity, R.string.err_login, Toast.LENGTH_SHORT).show()

    }

    private fun showSucces() {
        Toast.makeText(activity, R.string.succes, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}