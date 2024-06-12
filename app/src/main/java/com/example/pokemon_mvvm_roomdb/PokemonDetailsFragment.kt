package com.example.pokemon_mvvm_roomdb

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pokemon_mvvm_roomdb.databinding.FragmentPokemonDetailsBinding

class PokemonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =
            requireContext().getSharedPreferences("pokemon_pref", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the Pokemon ID from SharedPreferences
        val pokemonName = sharedPreferences.getString("pokemon_id", null)

        // Show the retrieved Pokemon name in a Toast
        pokemonName?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
}
