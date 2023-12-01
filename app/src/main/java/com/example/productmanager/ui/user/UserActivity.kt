package com.example.productmanager.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.productmanager.R
import com.example.productmanager.databinding.ActivityUserBinding
import com.example.productmanager.ui.admin.ui.incidences.IncidencesFragment
import com.example.productmanager.ui.login.LoginActivity
import com.example.productmanager.ui.user.home.HomeFragment
import com.example.productmanager.ui.user.home.HomeViewModel
import com.example.productmanager.ui.user.loands.LoandsFragment
import com.example.productmanager.ui.user.rental.RentalFragment
import com.example.productmanager.ui.user.returns.ReturnsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        email = bundle?.getString("email").toString()


        initHome(email)

        initListeners()


    }

    private fun initHome(email: String?) {
        val userFragmentViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        userFragmentViewModel.welcome_text = "Welcome " + email.toString()
    }

    fun getUser(): String {
        return email
    }

    private fun initListeners() {
        binding.topToolbarUser.setOnMenuItemClickListener {
            logout()
            true
        }

        replaceFragment(HomeFragment(), "HOME")
        binding.navViewUser.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment(), "HOME")
                R.id.navigation_rental -> replaceFragment(RentalFragment(), "RENTALS")
                R.id.navigation_returns -> replaceFragment(ReturnsFragment(), "RETURNS")
                R.id.navigation_loands -> replaceFragment(LoandsFragment(), "LOANDS")
                R.id.navigation_incidences -> replaceFragment(IncidencesFragment(), "INCIDENCES")
                else -> {

                }
            }
            true
        }
    }

    private fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))

    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        binding.topToolbarUser.setTitle(title)
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}