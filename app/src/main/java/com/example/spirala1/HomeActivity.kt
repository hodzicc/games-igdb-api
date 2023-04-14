package com.example.spirala1

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.spirala1.HomeFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var fragmentManager: FragmentManager
    private lateinit var bottomNav: BottomNavigationView
    private val sharedViewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .add(R.id.details_container, GameDetailsFragment())
                .add(R.id.home_container, HomeFragment())
                .commit()
        }
        else{
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            val navView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
            navView.setupWithNavController(navController)

            var currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)




        sharedViewModel.isGameDetailsOpened.observe(this) { isOpened ->
            if (isOpened) {
                navView.menu.findItem(R.id.gameDetailsItem).isEnabled = true
                navView.menu.findItem(R.id.gameDetailsItem)
                    .setOnMenuItemClickListener {
                        val currentFragment1 = navHostFragment?.childFragmentManager?.fragments?.last()
                        if(currentFragment1 is HomeFragment) {
                            val gametitle1 = sharedViewModel.gametitle.value ?: ""
                            val action = HomeFragmentDirections.homeToDetails(gametitle1)
                            navController.navigate(action)
                        }
                        true
                    }
            } else {
                navView.menu.findItem(R.id.gameDetailsItem).isEnabled = false
                navView.menu.findItem(R.id.gameDetailsItem).setOnMenuItemClickListener(null)
            }
        }
        }

        sharedViewModel.gametitle.observe(this) { gametitle ->
            if (gametitle.isNotEmpty()) {
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    val bundle = Bundle().apply {
                        putString("message", gametitle)
                    }
                    val fragment = GameDetailsFragment()
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .add(R.id.details_container, fragment)
                        .add(R.id.home_container, HomeFragment())
                        .commit()
                } else {
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    val currentFragment1 = navHostFragment?.childFragmentManager?.fragments?.last()
                    if(currentFragment1 is HomeFragment) {
                        val action = HomeFragmentDirections.homeToDetails(gametitle)
                        navController.navigate(action)
                    }

                }
            }
            else{
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    val bundle = Bundle().apply {
                        putString("gameTitle", "Fortnite")
                    }
                    val fragment = GameDetailsFragment()
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.details_container, fragment)
                        .commit()
                }
            }
        }




    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.details_container, GameDetailsFragment())
                .replace(R.id.home_container, HomeFragment())
                .commit()
        }
    }




}


