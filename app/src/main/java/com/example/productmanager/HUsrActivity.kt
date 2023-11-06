package com.example.productmanager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.productmanager.databinding.ActivityHomeUsrBinding
import com.example.productmanager.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class HUsrActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHomeUsrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeUsrBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //recovery data
        val bundle: Bundle? = intent.extras
        val email = bundle?.getString("email")
        setup(email?:"")

        getPreferences(email)
        mBinding.btnLogout.setOnClickListener{logOut() }
    }

    private fun getPreferences(email:String?) {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.apply()
    }
    private fun clearPreferences(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

    }

    private fun logOut() {
        clearPreferences()
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))

    }

    private fun setup(email:String){
        mBinding.txtWelcome.text = "Welcome " + email

    }



}