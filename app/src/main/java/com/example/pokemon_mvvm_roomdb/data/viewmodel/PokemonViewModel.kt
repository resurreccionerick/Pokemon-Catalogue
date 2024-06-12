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

    private val _pokemonList = MutableLiveData<PokemonList>()
    private val _pokemon = MutableLiveData<PokemonDetails>()

    val pokemonList: LiveData<PokemonList> get() = _pokemonList
    val pokemon: LiveData<PokemonDetails> get() = _pokemon

    fun fetchPokemonList() {
        viewModelScope.launch {
            val response = repository.fetchPokemonList()
            _pokemonList.postValue(response)
        }
    }

    fun fetchPokemon(id: Int) {
        viewModelScope.launch {
            val response = repository.fetchPokemon(id)
            _pokemon.postValue(response)
        }
    }
}
