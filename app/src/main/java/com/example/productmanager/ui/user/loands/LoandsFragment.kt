package com.example.productmanager.ui.user.loands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productmanager.R
import com.example.productmanager.databinding.UserFragmentLoandsBinding
import com.example.productmanager.domain.model.entities.RentalTool
import com.example.productmanager.ui.user.UserDataViewModel
import com.example.productmanager.ui.user.loands.adapter.LoandsAdapter
import com.example.productmanager.ui.user.loands.adapter.LoandsProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoandsFragment : Fragment() {

    private var _binding: UserFragmentLoandsBinding? = null
    private val loandsViewModel: LoandsViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var email = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserFragmentLoandsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initObservers()
        initListeners()

        initRecyclerView()


        return root
    }

    private fun initListeners() {
        binding.btnUpdate.setOnClickListener {
            loandsViewModel.updateLoands(email)
        }

    }

    private fun initObservers() {
        loandsViewModel.update.observe(viewLifecycleOwner) {
            if (it != null) {
                if(it.size == 0)  showNothingtoUpdate()
                else  updateRecyclerView(it)

            }
        }
        userDataViewModel.userMail.observe(viewLifecycleOwner) {
            email = it
        }
    }

    private fun showNothingtoUpdate() {
        Toast.makeText(activity, R.string.noloands, Toast.LENGTH_SHORT).show()
    }

    private fun updateRecyclerView(pendingTools: MutableList<RentalTool>) {
        val previousSize = LoandsProvider.loandsList.size
        // Update the data source
        LoandsProvider.loandsList.clear()
        LoandsProvider.loandsList.addAll(pendingTools)
        // Notify the adapter about the range of inserted items
        binding.rvListLoands.adapter?.notifyItemRangeInserted(previousSize, pendingTools.size)
    }

    private fun initRecyclerView() {
        binding.rvListLoands.layoutManager = LinearLayoutManager(context)
        LoandsProvider.loandsList.clear()
        binding.rvListLoands.adapter = LoandsAdapter(LoandsProvider.loandsList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}