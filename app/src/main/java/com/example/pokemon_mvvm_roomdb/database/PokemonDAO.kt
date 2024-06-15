package com.example.pagkain_mvvm.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemon.model.details.PokemonDetails

@Dao
interface PokemonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPokemon(meal: PokemonDetails)

    @Delete
    suspend fun delete(meal: PokemonDetails)

    @Query("SELECT * FROM pokemonInformation")
    fun getAllPokemon(): LiveData<List<PokemonDetails>>

}