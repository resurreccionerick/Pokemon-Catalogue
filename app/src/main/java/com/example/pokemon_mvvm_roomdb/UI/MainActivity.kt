package com.example.pokemon_mvvm_roomdb.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)


        val navFragmentController = Navigation.findNavController(this, R.id.navFragmentController)

        NavigationUI.setupWithNavController(binding.btmNav, navFragmentController)

    }
}