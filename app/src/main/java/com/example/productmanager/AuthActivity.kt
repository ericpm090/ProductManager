package com.example.productmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.productmanager.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 21
    private lateinit var mBinding: ActivityAuthBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnRegister.setOnClickListener{register()}
        mBinding.btnLoging.setOnClickListener{signIn()}

    }

   /* private fun register(){
        val user = mBinding.etUserMail.text
        val password = mBinding.etUserPassword.text

        if(user!!.isNotEmpty() && password!!.isNotEmpty()){
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(mBinding.etUserMail.text.toString(), mBinding.etUserPassword.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        showUserHome(it.result.user?.email ?: "")
                    }else{
                        showAlert()
                    }
                }
        }
    }*/

    private fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)

    }

    private fun showUserHome(email:String){
        val intent = Intent(this, HomeUserActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(intent)
    }

    private fun signIn(){
        val user = mBinding.etUserMail.text
        val password = mBinding.etUserPassword.text
        if(user!!.isNotEmpty() && password!!.isNotEmpty()){
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(mBinding.etUserMail.text.toString(), mBinding.etUserPassword.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        showUserHome(user.toString())
                    }else{
                        showAlert()
                    }
                }
        }


    }

    private fun showAlert(){
        Toast.makeText(this,R.string.err_login, Toast.LENGTH_SHORT).show()

    }

}