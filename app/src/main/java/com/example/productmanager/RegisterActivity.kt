package com.example.productmanager

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.productmanager.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.btnRegister.setOnClickListener{createAccount() }
        mBinding.btnBack.setOnClickListener{back()}
        auth = Firebase.auth


    }

    private fun createAccount() {
        val name = mBinding.etName.text
        val email = mBinding.etUserMail.text
        val password = mBinding.etUserPassword.text

        if(email!!.isNotEmpty() && password!!.isNotEmpty()){
            if(password.toString().length < 6) {
                showPasswordError()
            }else{
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(mBinding.etUserMail.text.toString(),
                        mBinding.etUserPassword.text.toString())
                    .addOnCompleteListener(this){ task->
                        if(task.isSuccessful){
                            showUserHome(task.result?.user?.email?:"")
                        }else{
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this,task.exception.toString(), Toast.LENGTH_SHORT).show()
                            //showAlert()
                        }
                    }
            }

        }

    }

    private fun showPasswordError() {
        Toast.makeText(this,"Password is too short. Please insert more than 6 characters", Toast.LENGTH_SHORT).show()
    }

    private fun showUserHome(email:String){
        val intent = Intent(this, HomeUserActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(intent)
    }
    private fun back(){
        startActivity(Intent(this, AuthActivity::class.java))
    }

    private fun showAlert(){
        Toast.makeText(this,R.string.err_login, Toast.LENGTH_SHORT).show()

    }

}