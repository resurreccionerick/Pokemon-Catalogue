package com.example.pokemon_mvvm_roomdb.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagkain_mvvm.database.PokemonDatabase
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PokemonRepository

    private val pokemonDatabase: PokemonDatabase = PokemonDatabase.getDatabase(application)

    private val _pokemonMutableLiveData = MutableLiveData<PokemonDetails?>()
    private val _pokemonListSpriteMutableLiveData = MutableLiveData<List<PokemonDetails>>()
    private val _favPokemonMutableLiveData: LiveData<List<PokemonDetails>>

    val pokemonListSpriteLiveData: LiveData<List<PokemonDetails>> get() = _pokemonListSpriteMutableLiveData
    val pokemonDetailsLiveData: LiveData<PokemonDetails?> get() = _pokemonMutableLiveData
    val favPokemonLiveData: LiveData<List<PokemonDetails>> get() = _favPokemonMutableLiveData

    init {
        val pokemonDetailsDao = pokemonDatabase.pokemonDetailsDao()
        repository = PokemonRepository.getInstance(RetrofitInstance.api, pokemonDetailsDao)
        _favPokemonMutableLiveData = pokemonDetailsDao.getAllPokemon()
    }

    val pokemonList: Flow<PagingData<PokemonDetails>> = Pager(PagingConfig(pageSize = 20)) {
        repository.getPokemonPagingSource()
    }.flow

    fun fetchPokemon(id: String) { //this is for details
        viewModelScope.launch {
            val response = repository.fetchPokemon(id)
            _pokemonMutableLiveData.postValue(response)
        }
    }

    fun fetchPokemonListWithSprites() { //pokemon list
        viewModelScope.launch {
            val pokemonList = repository.fetchPokemonListWithSprites()
            _pokemonListSpriteMutableLiveData.postValue(pokemonList)
        }
    }

    fun insertPokemon(pokemon: PokemonDetails) {
        viewModelScope.launch {
            val pokemonWithFavoriteStatus = pokemon.copy(isFavorite = true)
            repository.insertPokemon(pokemonWithFavoriteStatus)
        }
    }

    fun deletePokemon(pokemon: PokemonDetails) {
        viewModelScope.launch {
            repository.deletePokemon(pokemon)
        }
    }
}
