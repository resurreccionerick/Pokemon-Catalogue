// PokemonViewModel.kt
package com.example.pokemon_mvvm_roomdb.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon.model.list.PokemonList
import com.example.pokemon.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class PokemonViewModel() : ViewModel() {
    private val repository = PokemonRepository(RetrofitInstance.api)

    private val _pokemon = MutableLiveData<PokemonDetails>()
    private val _pokemonListSprite = MutableLiveData<List<PokemonDetails>>()

    val pokemonListSprite: LiveData<List<PokemonDetails>> get() = _pokemonListSprite
    val pokemon: LiveData<PokemonDetails> get() = _pokemon

    fun fetchPokemon(id: String) {
        viewModelScope.launch {
            val response = repository.fetchPokemon(id)!!
            _pokemon.postValue(response)
        }
    }

    fun fetchPokemonListWithSprites() {
        viewModelScope.launch {
            val pokemonList = repository.fetchPokemonListWithSprites()
            _pokemonListSprite.postValue(pokemonList)
        }
    }

}
