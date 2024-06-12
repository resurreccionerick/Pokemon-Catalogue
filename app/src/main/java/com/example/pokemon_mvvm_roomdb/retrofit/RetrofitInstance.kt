package com.example.pokemon.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: ApiService by lazy {
        Retrofit.Builder().baseUrl(
            "https://pokeapi.co/api/v2/"
        ).addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}