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

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(ToolsFragment())
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_tools -> replaceFragment(ToolsFragment())
                R.id.navigation_users -> replaceFragment(UsersFragment())
                R.id.navigation_projects -> replaceFragment(ProjectsFragment())
                R.id.navigation_locations -> replaceFragment(LocationsFragment())
                R.id.navigation_incidences -> replaceFragment(IncidencesFragment())
                else -> {

                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


}