package com.example.productmanager.ui.login


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.productmanager.HUsrActivity
import com.example.productmanager.R
import com.example.productmanager.ui.signin.SignInActivity
import com.example.productmanager.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel:LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }
    private fun initUI(){
        initObservers()
        initListeners()

    }
    private fun initListeners(){
        //binding.btnRegister.setOnClickListener{register()}
        //binding.btnLoging.setOnClickListener{signIn()}

        binding.btnRegister.setOnClickListener{goToSignIn()}
        binding.btnLoging.setOnClickListener{
            loginViewModel.onLoginSelected(
            binding.etUserMail.toString(),
            binding.etUserPassword.toString())}

        //binding.googleSingin.setOnClickListener{signInWithGoogle()}
    }

    private fun initObservers(){
        loginViewModel.navigateToHomeUser.observe(this) {
            if (it.toString() == "FALSE") showAlert()
            else goToUserHome(it.toString())
        }
    }


   /*private fun signIn(){

       val user = binding.etUserMail.text
       val password = binding.etUserPassword.text
       if(user!!.isNotEmpty() && password!!.isNotEmpty()){
           FirebaseAuth.getInstance()
               .signInWithEmailAndPassword(binding.etUserMail.text.toString(), binding.etUserPassword.text.toString())
               .addOnCompleteListener{
                   if(it.isSuccessful){
                       goToUserHome(user.toString())
                   }else{
                       showAlert()
                   }
               }
       }
   }*/
    //private fun signInWithGoogle() {

    //}

    private fun goToSignIn(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)

    }
    private fun goToUserHome(email:String){
        val intent = Intent(this, HUsrActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(intent)
    }

    private fun showAlert(){
        Toast.makeText(this, R.string.err_login, Toast.LENGTH_SHORT).show()

    }

}