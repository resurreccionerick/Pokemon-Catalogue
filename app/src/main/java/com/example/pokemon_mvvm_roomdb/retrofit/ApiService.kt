package com.example.pokemon.retrofit

import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon.model.list.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(): PokemonList

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonDetails

}