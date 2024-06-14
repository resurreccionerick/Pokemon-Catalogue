package com.example.pokemon_mvvm_roomdb.UI.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pagkain_mvvm.database.PokemonDatabase
import com.example.pokemon_mvvm_roomdb.data.viewmodel.PokemonViewModel

class PokemonViewModelFactory(private val pokemonDatabase: PokemonDatabase) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return PokemonViewModel(pokemonDatabase) as T
//    }
}