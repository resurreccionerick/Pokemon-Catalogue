package com.example.pokemon.model.details

import com.google.gson.annotations.SerializedName

data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val types: List<Type>,
    val moves: List<Move>,
    val sprites: Sprites
)

data class Ability(
    val ability: AbilityInfo
)

data class AbilityInfo(
    val name: String
)

data class Type(
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)

data class Move(
    val move: MoveInfo
)

data class MoveInfo(
    val name: String
)

data class Sprites(
    val front_default: String,
    val back_default: String // Added back sprite as an example
)
//    val name: String,
//    val weight: Int,
//    val height: Int,
//    val types: List<TypeItem>,
//    val sprites: Sprite
//)
//
//data class TypeItem(val type: Type)
//
//data class Type(val name: String, val url: String)
//
//data class Sprite(
//    @SerializedName("front_default")
//    val frontDefault: String
//)