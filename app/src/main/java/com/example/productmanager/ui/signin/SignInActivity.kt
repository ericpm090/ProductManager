package com.example.productmanager.ui.signin


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.productmanager.HUsrActivity
import com.example.productmanager.R
import com.example.productmanager.databinding.ActivitySigninBinding
import com.example.productmanager.domain.model.Employee
import com.example.productmanager.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private val signInViewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dismissKeyboardShortcutsHelper()
        initUI()
    }

    private fun initUI() {
        initObservers()
        initListeners()
    }

    private fun initListeners() {

        binding.btnRegister.setOnClickListener {
            signInViewModel.onSignInSelected(
                Employee(
                    name = binding.etName.text.toString(),
                    email = binding.etUserMail.text.toString(),
                    password = binding.etUserPassword.text.toString()
                )
            )
        }
        binding.btnBack.setOnClickListener { goToBack() }

    }

    private fun initObservers() {
        signInViewModel.navigateToHomeUser.observe(this) {
            if (it){
                goToUserHome(binding.etUserMail.text.toString())
            }
            else showAlert()
        }
    }

    private fun goToUserHome(email: String) {
        val intent = Intent(this, HUsrActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
    }

    private fun goToBack() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun showAlert() {
        Toast.makeText(this, R.string.err_login, Toast.LENGTH_SHORT).show()

    }

}