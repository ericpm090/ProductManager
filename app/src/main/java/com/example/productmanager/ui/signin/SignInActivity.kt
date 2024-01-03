package com.example.productmanager.ui.signin


import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.productmanager.R
import com.example.productmanager.databinding.ActivitySigninBinding
import com.example.productmanager.ui.user.UserActivity
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
                binding.etUserMail.text.toString(),
                binding.etName.text.toString(),
                binding.etUserPassword.text.toString()
            )

        }
        binding.btnBack.setOnClickListener { goToBack() }

    }

    private fun initObservers() {
        signInViewModel.navigateToHomeUser.observe(this) {
            if (it == true) {
                goToUserHome(binding.etUserMail.text.toString())
            } else showAlert()
        }
        signInViewModel.exceptionsSignIn.observe(this) {
            binding.email.helperText = it
            binding.email.setHelperTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        R.color.red_error
                    )
                )
            )

        }
    }

    private fun goToUserHome(email: String) {
        val intent = Intent(this, UserActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
    }

    private fun goToBack() {
        finish()
    }


    private fun showAlert() {
        Toast.makeText(this, R.string.err_login, Toast.LENGTH_SHORT).show()

    }


}