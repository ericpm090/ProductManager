package com.example.productmanager.ui.admin.ui.toolHistory_screen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productmanager.databinding.ActivityHistoryToolBinding
import com.example.productmanager.ui.admin.ui.incidences.adapter.HistoryToolAdapter
import com.example.productmanager.ui.admin.ui.incidences.adapter.HistoryToolProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryToolActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryToolBinding
    private val historyToolViewModel: HistoryToolViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryToolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val barcode = bundle?.getString("barcode")

        initListeners()
        initScreen(barcode)
        initObservers()

    }
    /*
    initListeners: Dedicated method to detect actions of UI.
    Makes calls to methods of viewmodel to obtain results

     */
    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            goToPreviosScreen()
        }
    }

    private fun initScreen(barcode: String?) {
        HistoryToolProvider.rentalToolList.clear()
        binding.rvHistoryTool.adapter?.notifyDataSetChanged()
        if (barcode != null) historyToolViewModel.onGetHistoryToolSelected(barcode)

    }

    private fun initObservers() {
        historyToolViewModel.historyList.observe(this) {
            HistoryToolProvider.rentalToolList.addAll(it)
            initRecyclerView()
        }

    }

    private fun initRecyclerView() {

        binding.rvHistoryTool.layoutManager = LinearLayoutManager(this)
        binding.rvHistoryTool.adapter = HistoryToolAdapter(
            HistoryToolProvider.rentalToolList,
        )

    }

    private fun goToPreviosScreen() {
        finish()
    }

}