package com.example.productmanager.ui.login


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.productmanager.HUsrActivity
import com.example.productmanager.R
import com.example.productmanager.databinding.ActivityLoginBinding
import com.example.productmanager.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dismissKeyboardShortcutsHelper()
        initUI()

    }

    private fun initUI() {
        initObservers()
        initListeners()

    }

    private fun initListeners() {

        binding.btnRegister.setOnClickListener { goToSignIn() }
        binding.btnLoging.setOnClickListener {
            loginViewModel.onLoginSelected(
                binding.etUserMail.toString(),
                binding.etUserPassword.toString()
            )
        }

    }

    private fun initObservers() {
        loginViewModel.navigateToHomeUser.observe(this) {
            if (it.toString() == "FALSE") showAlert()
            else goToUserHome(it.toString())
        }
    }

    private fun goToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)

    }

    private fun goToUserHome(email: String) {
        val intent = Intent(this, HUsrActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
    }

    private fun showAlert() {
        Toast.makeText(this, R.string.err_login, Toast.LENGTH_SHORT).show()

    }

}