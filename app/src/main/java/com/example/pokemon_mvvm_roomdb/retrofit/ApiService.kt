package com.example.pokemon.retrofit

import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon.model.list.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonList

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: String): PokemonDetails

}