package com.example.pokemon_mvvm_roomdb.UI.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.navFragmentController)
        setupBottomNavMenu()
    }

    private fun setupBottomNavMenu() {
        NavigationUI.setupWithNavController(binding.btmNav, navController)

        //hide nav view if in details
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.pokemonDetailsFragment) {
                binding.btmNav.visibility = View.GONE
            } else {
                binding.btmNav.visibility = View.VISIBLE
            }
        }
    }
}
