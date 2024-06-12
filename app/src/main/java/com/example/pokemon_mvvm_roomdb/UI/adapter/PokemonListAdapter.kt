package com.example.pokemon_mvvm_roomdb.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.model.list.Result
import com.example.pokemon_mvvm_roomdb.databinding.PokemonItemBinding

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    lateinit var onItemClick: ((Result) -> Unit)

    private var pokemonList = ArrayList<Result>()

    fun setData(pokemonList: List<Result>) {
        this.pokemonList = pokemonList as ArrayList<Result>
        notifyDataSetChanged()
    }

    class PokemonViewHolder(val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            PokemonItemBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                )
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonListAdapter.PokemonViewHolder, position: Int) {
        val item = pokemonList[position]

        holder.binding.tvPokemonName.text = item.name

        holder.itemView.setOnClickListener {
            onItemClick.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

}
