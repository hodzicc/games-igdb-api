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
    private var temp: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            temp=sharedViewModel.gametitle.value
            if(temp=="") {
                temp = "Fortnite"
                sharedViewModel.empty.value=true
            }
            else sharedViewModel.empty.value=false
           sharedViewModel.gametitle.value=temp
        }
        else{

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            val navView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
            navView.setupWithNavController(navController)

            var currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

           // sharedViewModel.gametitle.value=""
            if(sharedViewModel.empty.value==true)
                temp = ""
            else
            temp=sharedViewModel.gametitle.value

            sharedViewModel.gametitle.value=temp

            //println("shhh")
            println(sharedViewModel.gametitle.value)

            


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
            if (gametitle!="") {
               //
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                  //  println("uso u gametitle")
                    val bundle = Bundle().apply {
                        putString("message", gametitle)
                    }
                    val fragment = GameDetailsFragment()
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.details_container, fragment)
                        .replace(R.id.home_container, HomeFragment())
                        .commit()
                } else {
                    sharedViewModel.empty.value=false
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
                else{
                    if(sharedViewModel.empty.value==true) {
                        val navHostFragment =
                            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                        val currentFragment1 =
                            navHostFragment?.childFragmentManager?.fragments?.last()
                        // println("ok")
                        if (currentFragment1 is GameDetailsFragment) {
                            val action = GameDetailsFragmentDirections.gameToHome()
                            navController.navigate(action)
                        }
                    }
                }

            }
        }




    }


  /*  override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val bundle = Bundle().apply {
                putString("gameTitle", "Fortnite")
            }
            val fragment = GameDetailsFragment()
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.details_container, fragment)
                .replace(R.id.home_container, HomeFragment())
                .commit()
            println("radi radi")
        }
        else {
            val fragment = HomeFragment()
            supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            // Start a new transaction and add the HomeFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
        }
    }


   */



}


