package com.example.pagkain_mvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemon.model.details.PokemonDetails

@Database(entities = [PokemonDetails::class], version = 1, exportSchema = false)
//@TypeConverters(TypeConverter::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun dao(): PokemonDAO

    companion object {
        @Volatile
        var INSTANCE: PokemonDatabase? = null //will be visible to any other thread

        @Synchronized //only one thread can have instance of this db
        fun getInstance(context: Context): PokemonDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    PokemonDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration() //rebuild db but keeping the data inside the db
                    .build()
            }
            return INSTANCE as PokemonDatabase
        }
    }

}