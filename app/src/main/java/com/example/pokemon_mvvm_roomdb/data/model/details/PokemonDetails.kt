package com.example.pokemon.model.details

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemonInformation")
data class PokemonDetails(
    @PrimaryKey()
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val types: List<Type>,
    val moves: List<Move>,
    val sprites: Sprites,
    val stats: List<Stat>,
    var isFavorite: Boolean
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
    val back_default: String
)

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)
