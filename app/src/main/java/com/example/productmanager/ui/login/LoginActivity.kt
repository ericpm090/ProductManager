package com.example.productmanager.ui.login


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.productmanager.HUsrActivity
import com.example.productmanager.R
import com.example.productmanager.databinding.ActivityLoginBinding
import com.example.productmanager.ui.admin.AdminActivity
import com.example.productmanager.ui.signin.SignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    //private val GOOGLE_SIGN_IN = 100

    private companion object {
        const val ADMIN_ACCOUNT = "admin"
    }

    private val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                loginViewModel.onLoginGoogleSelected(it.data)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dismissKeyboardShortcutsHelper()
        //goToAdminPanel(ADMIN_ACCOUNT) //remove after testing
        session()
        initUI()

    }

    override fun onStart() {
        super.onStart()
        binding.viewLoging.visibility = View.VISIBLE
    }

    private fun initUI() {

        initObservers()
        initListeners()
    }

    //Miramos si ya habia una sesion iniciada. Si la hay se va directamente a la home de user
    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        if (email != null) {
            binding.viewLoging.visibility = View.INVISIBLE
            goToUserHome(email.toString())
        }

    }

    private fun initListeners() {


        binding.btnRegister.setOnClickListener { goToSignIn() }
        binding.btnLoging.setOnClickListener {
            if ((binding.etUserMail.text.toString() == ADMIN_ACCOUNT) && (binding.etUserPassword.text.toString() == ADMIN_ACCOUNT)) {
                goToAdminPanel()
            } else {
                loginViewModel.onLoginSelected(
                    binding.etUserMail.toString(),
                    binding.etUserPassword.toString()
                )
            }

        }

        binding.googleSingin.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()


            result.launch(googleClient.signInIntent)

        }

    }

    private fun initObservers() {
        loginViewModel.navigateToHomeUser.observe(this) {
            if (it) goToUserHome(binding.etUserMail.text.toString())
            else showAlert()
        }

        loginViewModel.navigateToSignInGoogle.observe(this) {
            if (it) goToUserHome(loginViewModel.getUser()!!.email.toString())
            else showAlert()

        }

    }

    private fun goToAdminPanel() {
        Log.i("LoginActivity", "User loged as admin")
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }

    private fun goToSignIn() {
        Log.i("LoginActivity", "User not registered, go to signInActivity")
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)

    }


    private fun goToUserHome(email: String) {
        Log.i("LoginActivity", "User loged as employee")

        val intent = Intent(this, HUsrActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
    }


    private fun showAlert() {


        Toast.makeText(this, R.string.err_login, Toast.LENGTH_SHORT).show()

    }


}