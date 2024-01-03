package com.example.productmanager.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.productmanager.R
import com.example.productmanager.databinding.ActivityAdminBinding
import com.example.productmanager.ui.admin.ui.incidences.IncidencesFragment
import com.example.productmanager.ui.admin.ui.locations.LocationsFragment
import com.example.productmanager.ui.admin.ui.projects.ProjectsFragment
import com.example.productmanager.ui.admin.ui.tools.ToolsFragment
import com.example.productmanager.ui.admin.ui.users.UsersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initListeners()


    }

    private fun initListeners() {
        binding.topToolbar.setOnMenuItemClickListener {
            logout()
            true
        }

        replaceFragment(ToolsFragment(), "TOOLS")
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_tools -> replaceFragment(ToolsFragment(), "TOOLS")
                R.id.navigation_users -> replaceFragment(UsersFragment(), "USERS")
                R.id.navigation_projects -> replaceFragment(ProjectsFragment(), "PROJECTS")
                R.id.navigation_locations -> replaceFragment(LocationsFragment(), "LOCATIONS")
                R.id.navigation_incidences -> replaceFragment(IncidencesFragment(), "INCIDENCES")
                else -> {

                }
            }
            true
        }

    }

    private fun logout() {
        finish()
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        binding.topToolbar.setTitle(title)
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


}