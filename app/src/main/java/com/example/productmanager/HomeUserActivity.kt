package com.example.productmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.productmanager.databinding.ActivityAuthBinding
import com.example.productmanager.databinding.ActivityHomeUserBinding
import com.google.firebase.auth.FirebaseAuth


class HomeUserActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHomeUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //recovery data
        val bundle: Bundle? = intent.extras
        val email = bundle?.getString("email")
        setup(email?:"")

        mBinding.btnLogout.setOnClickListener{logOut() }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, AuthActivity::class.java))

    }

    private fun setup(email:String){
        mBinding.txtWelcome.text = "Welcome " + email

    }



}