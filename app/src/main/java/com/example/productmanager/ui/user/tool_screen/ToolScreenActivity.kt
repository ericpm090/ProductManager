package com.example.productmanager.ui.user.tool_screen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.productmanager.databinding.ActivityToolSreenBinding
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
        val status= bundle?.getString("status")

        initScreen(photo, name,barcode,project,status)


        //initObservers()
        initListeners()

    }

    private fun initListeners() {
        binding.btnBook.setOnClickListener {
            Log.i("ToolScreenActivity", "Buttom book selected")
        }
    }

    private fun initObservers() {

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
        binding.tvStatus.text = status
    }

}