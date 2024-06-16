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
        try {
            val offset = params.key ?: 0
            val response = apiService.getPokemonList(offset, params.loadSize)
            val pokemonList = response.results.mapIndexed { index, result ->
                PokemonDetails(
                    id = offset + index + 1,
                    name = result.name,
                    height = 0,  // Initialize these values with default
                    weight = 0,
                    abilities = emptyList(),
                    types = emptyList(),
                    moves = emptyList(),
                    sprites = Sprites("", ""),  // Initialize with default URLs
                    stats = emptyList(),
                    isFavorite = false
                )
            }
            return LoadResult.Page(
                data = pokemonList,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = offset + params.loadSize
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
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
