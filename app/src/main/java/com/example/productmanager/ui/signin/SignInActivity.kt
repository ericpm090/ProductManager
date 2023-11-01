package com.example.productmanager.ui.signin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.activity.viewModels
import com.example.productmanager.HUsrActivity
import com.example.productmanager.R

import com.example.productmanager.databinding.ActivitySigninBinding
import com.example.productmanager.domain.model.Employee
import com.example.productmanager.ui.login.LoginActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySigninBinding
    //private lateinit var auth:FirebaseAuth
    private val signInViewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    private fun initUI(){
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        //binding.btnRegister.setOnClickListener{createAccount() }

        binding.btnRegister.setOnClickListener{ signInViewModel.onSignInSelected(
            Employee(
                name = binding.etName.toString(),
                email = binding.etUserMail.toString(),
                password = binding.etUserPassword.toString())
        ) }
        binding.btnBack.setOnClickListener{goToBack()}
        //auth = Firebase.auth
    }

    private fun initObservers(){

        signInViewModel.navigateToHomeUser.observe(this){
            if(it) goToUserHome(binding.etUserMail.toString())
            else showAlert()
        }


    }

   /* private fun createAccount() {
        val name = binding.etName.text
        val email = binding.etUserMail.text
        val password = binding.etUserPassword.text

        if(email!!.isNotEmpty() && password!!.isNotEmpty()){
            if(password.toString().length < 6) {
                showPasswordError()
            }else{
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(binding.etUserMail.text.toString(),
                        binding.etUserPassword.text.toString())
                    .addOnCompleteListener(this){ task->
                        if(task.isSuccessful){
                            goToUserHome(task.result?.user?.email?:"")
                        }else{
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this,task.exception.toString(), Toast.LENGTH_SHORT).show()
                            //showAlert()
                        }
                    }
            }

        }

    }*/

    private fun showPasswordError() {
        Toast.makeText(this,"Password is too short. Please insert more than 6 characters", Toast.LENGTH_SHORT).show()
    }

    private fun goToUserHome(email:String){
        val intent = Intent(this, HUsrActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(intent)
    }
    private fun goToBack(){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun showAlert(){
        Toast.makeText(this, R.string.err_login, Toast.LENGTH_SHORT).show()

    }

}