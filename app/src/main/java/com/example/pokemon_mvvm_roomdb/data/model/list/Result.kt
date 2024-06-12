package com.example.pokemon.model.list


import com.example.pokemon.model.details.Sprites
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("sprites")
    val sprites: Sprites
)