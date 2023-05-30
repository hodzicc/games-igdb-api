package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var fragmentManager: FragmentManager
    private lateinit var bottomNav: BottomNavigationView
    private val sharedViewModel: SharedViewModel by viewModels()
    private var temp: String? = null



    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            temp=sharedViewModel.gametitle.value
           sharedViewModel.gametitle.value=temp
        }
        else{

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            val navView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
            navView.setupWithNavController(navController)

            var currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)


            temp=sharedViewModel.gametitle.value

            sharedViewModel.gametitle.value=temp


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

                    val bundle = Bundle().apply {
                        putString("message", gametitle)
                    }
                    val fragment = GameDetailsFragment()
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.details_container, fragment)
                        .replace(R.id.home_container, HomeFragment())
                        .commit()

                    sharedViewModel.isGameDetailsOpened.value=true

                } else {

                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    val currentFragment1 = navHostFragment?.childFragmentManager?.fragments?.last()
                    if(currentFragment1 is HomeFragment) {
                        val action = HomeFragmentDirections.homeToDetails(gametitle)
                        navController.navigate(action)
                    }
                    else{
                        val action = GameDetailsFragmentDirections.actionGameDetailsItemSelf(gametitle)
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
                        .replace(R.id.home_container, HomeFragment())
                        .replace(R.id.details_container, fragment)
                        .commit()
                }
                else{

                        val navHostFragment =
                            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                        val currentFragment1 =
                            navHostFragment?.childFragmentManager?.fragments?.last()

                        if (currentFragment1 is GameDetailsFragment) {
                            val action = GameDetailsFragmentDirections.gameToHome()
                            navController.navigate(action)
                        }
                }

            }
        }

    }

}


