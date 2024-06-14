package com.example.pagkain_mvvm.database

import androidx.room.TypeConverter
import com.example.pokemon.model.details.Ability
import com.example.pokemon.model.details.Move
import com.example.pokemon.model.details.Sprites
import com.example.pokemon.model.details.Stat
import com.example.pokemon.model.details.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromAbilityList(abilities: List<Ability>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Ability>>() {}.type
        return gson.toJson(abilities, type)
    }

    @TypeConverter
    fun toAbilityList(abilityString: String): List<Ability> {
        val gson = Gson()
        val type = object : TypeToken<List<Ability>>() {}.type
        return gson.fromJson(abilityString, type)
    }

    @TypeConverter
    fun fromTypeList(types: List<Type>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Type>>() {}.type
        return gson.toJson(types, type)
    }

    @TypeConverter
    fun toTypeList(typeString: String): List<Type> {
        val gson = Gson()
        val type = object : TypeToken<List<Type>>() {}.type
        return gson.fromJson(typeString, type)
    }

    @TypeConverter
    fun fromMoveList(moves: List<Move>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Move>>() {}.type
        return gson.toJson(moves, type)
    }

    @TypeConverter
    fun toMoveList(moveString: String): List<Move> {
        val gson = Gson()
        val type = object : TypeToken<List<Move>>() {}.type
        return gson.fromJson(moveString, type)
    }

    @TypeConverter
    fun fromStatList(stats: List<Stat>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Stat>>() {}.type
        return gson.toJson(stats, type)
    }

    @TypeConverter
    fun toStatList(statString: String): List<Stat> {
        val gson = Gson()
        val type = object : TypeToken<List<Stat>>() {}.type
        return gson.fromJson(statString, type)
    }

    @TypeConverter
    fun fromSprites(sprites: Sprites): String {
        val gson = Gson()
        return gson.toJson(sprites)
    }

    @TypeConverter
    fun toSprites(spritesString: String): Sprites {
        val gson = Gson()
        return gson.fromJson(spritesString, Sprites::class.java)
    }
}
