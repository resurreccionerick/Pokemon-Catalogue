package com.example.pokemon_mvvm_roomdb.UI.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.databinding.PokemonItemBinding

class PokemonFavAdapter : RecyclerView.Adapter<PokemonFavAdapter.FavoritesViewHolder>() {

    lateinit var onItemClick: ((PokemonDetails) -> Unit)
    lateinit var onDeleteItemClick: ((PokemonDetails) -> Unit)

    var pokemonList = ArrayList<PokemonDetails>()

    class FavoritesViewHolder(val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = PokemonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonFavAdapter.FavoritesViewHolder, position: Int) {
        val item = pokemonList[position]

        holder.binding.tvPokemonName.text = item.name.toUpperCase()

        val (startColor, endColor) = getTypeGradientColors(item.types.firstOrNull()?.type?.name)
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(startColor, endColor)
        )
        holder.binding.tvPokemonName.background = gradientDrawable

        Glide.with(holder.itemView.context)
            .load(item.sprites.front_default)
            .into(holder.binding.pokemonImg)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(item)
        }

        holder.itemView.setOnLongClickListener {
            onDeleteItemClick.invoke(item)
            true
        }

    }

    private fun getTypeGradientColors(typeName: String?): Pair<Int, Int> {
        return when (typeName?.toLowerCase()) {
            "normal" -> Pair(Color.LTGRAY, Color.TRANSPARENT)
            "fire" -> Pair(Color.WHITE, Color.RED)
            "water" -> Pair(Color.WHITE, Color.BLUE)
            "electric" -> Pair(Color.WHITE, Color.YELLOW)
            "grass" -> Pair(Color.WHITE, Color.GREEN)
            "ice" -> Pair(Color.WHITE, Color.CYAN)
            "fighting", "ground" -> Pair(Color.WHITE, Color.rgb(156, 102, 31)) // Brown
            "poison" -> Pair(Color.WHITE, Color.rgb(128, 0, 128)) // Purple
            "flying" -> Pair(Color.WHITE, Color.LTGRAY)
            "psychic" -> Pair(Color.WHITE, Color.MAGENTA)
            "bug" -> Pair(Color.WHITE, Color.rgb(0, 128, 0)) // Dark green
            "rock" -> Pair(Color.WHITE, Color.GRAY)
            "ghost" -> Pair(Color.WHITE, Color.rgb(128, 128, 128)) // Gray
            "dragon" -> Pair(Color.WHITE, Color.rgb(75, 0, 130)) // Indigo
            "dark" -> Pair(Color.WHITE, Color.rgb(25, 25, 112)) // Midnight blue
            "steel" -> Pair(Color.WHITE, Color.rgb(169, 169, 169)) // Dark gray
            "fairy" -> Pair(Color.WHITE, Color.rgb(255, 192, 203)) // Pink
            else -> Pair(Color.TRANSPARENT, Color.TRANSPARENT) // Default transparent colors
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun setFavorites(list: ArrayList<PokemonDetails>) {
        this.pokemonList = list
        notifyDataSetChanged()
        Log.d("Meal search  ", list.toString())
    }

    fun removeItem(position: Int) {
        pokemonList.removeAt(position)
        notifyItemRemoved(position)
    }

}