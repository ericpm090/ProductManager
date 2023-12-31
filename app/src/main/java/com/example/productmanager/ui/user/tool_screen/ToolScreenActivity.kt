package com.example.productmanager.ui.user.tool_screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.productmanager.R
import com.example.productmanager.databinding.ActivityToolSreenBinding
import com.example.productmanager.domain.model.entities.ToolStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToolScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityToolSreenBinding
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToolSreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle: Bundle? = intent.extras
        val photo = bundle?.getString("photo")
        val name = bundle?.getString("name")
        val barcode = bundle?.getString("barcode")
        val project = bundle?.getString("project")
        val status = bundle?.getString("status")

        initScreen(photo, name, barcode, project, status)


        //initObservers()
        initListeners()

    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            goToPreviosScreen()
        }
    }


    private fun goToPreviosScreen() {
        finish()
    }


    private fun initScreen(
        photo: String?,
        name: String?,
        barcode: String?,
        project: String?,
        status: String?
    ) {


        Glide.with(binding.imgTool.context).load(photo).into(binding.imgTool)


        binding.tvName.text = name
        binding.tvBarcode.text = barcode
        binding.tvProject.text = project
        if (status == ToolStatus.AVAILABLE.toString()) binding.tvStatus.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.green
            )
        )
        else binding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.red))
        binding.tvStatus.text = status
    }

}