package com.example.pokemon_mvvm_roomdb.data.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon.model.details.Sprites
import com.example.pokemon.retrofit.ApiService

class PokemonPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, PokemonDetails>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonDetails> {
        return try {
            val offset = params.key ?: 0
            val response = apiService.getPokemonList(offset, params.loadSize)

            val pokemonDetailsList = response.results.map { result ->
                apiService.getPokemon(result.name) ?: PokemonDetails(
                    id = 0,
                    name = "",
                    height = 0,
                    weight = 0,
                    abilities = emptyList(),
                    types = emptyList(),
                    moves = emptyList(),
                    sprites = Sprites("", ""),
                    stats = emptyList(),
                    isFavorite = false
                )
            }

            LoadResult.Page(
                data = pokemonDetailsList,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (pokemonDetailsList.isEmpty()) null else offset + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, PokemonDetails>): Int? {
        // This method is used to return the key for the most recent page
        // (or null if there is no key), which will be used for subsequent
        // load operations after a refresh event.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
