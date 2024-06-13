package com.example.pokemon_mvvm_roomdb.UI.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.databinding.ActivitySpashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load(R.drawable.splash)
            .into(
                binding.imgSplash
            )

        startActivityWithDelay()
    }

    private fun startActivityWithDelay() {
        lifecycleScope.launch {
            delay(3000L)  // Delay for 3 seconds
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        }
    }
}