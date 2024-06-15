package com.example.pokemon_mvvm_roomdb.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pagkain_mvvm.database.PokemonDatabase
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class PokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PokemonRepository
    private val pokemonDatabase: PokemonDatabase

    private val _pokemon = MutableLiveData<PokemonDetails?>()
    private val _pokemonListSprite = MutableLiveData<List<PokemonDetails>>()
    private val _favPokemon: LiveData<List<PokemonDetails>>

    val pokemonListSprite: LiveData<List<PokemonDetails>> get() = _pokemonListSprite
    val pokemon: LiveData<PokemonDetails?> get() = _pokemon
    val favPokemon: LiveData<List<PokemonDetails>> get() = _favPokemon

    init {
        pokemonDatabase = PokemonDatabase.getDatabase(application)
        val pokemonDetailsDao = pokemonDatabase.pokemonDetailsDao()
        repository = PokemonRepository.getInstance(RetrofitInstance.api, pokemonDetailsDao)
        _favPokemon = pokemonDetailsDao.getAllPokemon()
    }

    fun fetchPokemon(id: String) {
        viewModelScope.launch {
            val response = repository.fetchPokemon(id)
            _pokemon.postValue(response)
        }
    }

    fun fetchPokemonListWithSprites() {
        viewModelScope.launch {
            val pokemonList = repository.fetchPokemonListWithSprites()
            _pokemonListSprite.postValue(pokemonList)
        }
    }

    fun insertPokemon(pokemon: PokemonDetails) {
        viewModelScope.launch {
            repository.insertPokemon(pokemon)
        }
    }

    fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        viewModelScope.launch {
         //   repository.updateFavoriteStatus(id, isFavorite)
        }
    }
}
