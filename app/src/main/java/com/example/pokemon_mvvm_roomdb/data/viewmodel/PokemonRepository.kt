package com.example.pokemon_mvvm_roomdb.data.viewmodel

import android.util.Log
import androidx.paging.PagingSource
import com.example.pagkain_mvvm.database.PokemonDAO
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(
    private val api: ApiService,
    private val pokemonDetailsDao: PokemonDAO
) {

    suspend fun fetchPokemon(id: String): PokemonDetails? {
        return withContext(Dispatchers.IO) {
            try {
                api.getPokemon(id)
            } catch (e: Exception) {
                Log.e("fetchPokemon error: ", e.message.toString())
                null
            }
        }
    }

    suspend fun fetchPokemonListWithSprites(): List<PokemonDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemonList = api.getPokemonList(0,20) // Fetch the list of Pokémon
                val pokemonDetailsList = mutableListOf<PokemonDetails>()
                pokemonList.results.forEach { pokemon ->
                    val pokemonDetails = api.getPokemon(pokemon.name)
                    pokemonDetails?.let {
                        pokemonDetailsList.add(it)
                        if (it.sprites != null) {
                            Log.e("fetchPokemonList img: ", it.sprites.toString())
                        } else {
                            Log.e("fetchPokemonList", "NULL")
                        }
                    }
                }
                pokemonDetailsList
            } catch (e: Exception) {
                Log.e("fetchPokemonList error: ", e.message.toString())
                emptyList()
            }
        }
    }

    suspend fun insertPokemon(pokemon: PokemonDetails) {
        pokemonDetailsDao.upsertPokemon(pokemon)
    }

    suspend fun deletePokemon(pokemon: PokemonDetails) {
        pokemonDetailsDao.delete(pokemon)
    }

    fun getPokemonPagingSource(): PagingSource<Int, PokemonDetails> {
        return PokemonPagingSource(api)
    }

    companion object {
        private var INSTANCE: PokemonRepository? = null

        fun getInstance(apiService: ApiService, pokemonDetailsDao: PokemonDAO): PokemonRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = PokemonRepository(apiService, pokemonDetailsDao)
                INSTANCE = instance
                instance
            }
        }
    }
}
