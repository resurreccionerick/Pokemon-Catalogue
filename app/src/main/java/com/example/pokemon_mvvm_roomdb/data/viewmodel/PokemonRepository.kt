package com.example.pokemon_mvvm_roomdb.data.viewmodel

import android.util.Log
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon.model.list.PokemonList
import com.example.pokemon.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(private val api: ApiService) {

    suspend fun fetchPokemonList(): PokemonList? {
        return withContext(Dispatchers.IO) {
            try {
                api.getPokemonList()
            } catch (e: Exception) {
                Log.e("fetchPokemonList error: ", e.message.toString())
                null
            }
        }
    }

    suspend fun fetchPokemon(id: Int): PokemonDetails? {
        return withContext(Dispatchers.IO) {
            try {
                api.getPokemon(id)
            } catch (e: Exception) {
                Log.e("fetchPokemon error: ", e.message.toString())
                null
            }
        }
    }
}
