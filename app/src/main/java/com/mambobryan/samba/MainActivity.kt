package com.mambobryan.samba

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mambobryan.samba.databinding.ActivityMainBinding
import com.mambobryan.samba.ui.characters.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val characterVm: CharactersViewModel by viewModels()
    private val noteVm: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHost.id) as NavHostFragment

        navController = navHostFragment.navController

        binding.navBottom.setupWithNavController(navController)

        setUpDestinationListener()

    }

    private fun setUpDestinationListener() {
        val destinationChangedListener =
            NavController.OnDestinationChangedListener { _: NavController?, destination: NavDestination, _: Bundle? ->
                when (destination.id) {
                    R.id.composeFragment -> {
                        hideBottomNavigation()
                    }
                    else -> {
                        showBottomNavigation()
                    }
                }
            }
        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    private fun hideBottomNavigation() = binding.navBottom.apply {
        visibility = View.GONE
    }

    private fun showBottomNavigation() = binding.navBottom.apply {
        visibility = View.VISIBLE
    }


}