package com.example.pokemon_mvvm_roomdb.UI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.UI.adapter.PokemonFavAdapter
import com.example.pokemon_mvvm_roomdb.data.viewmodel.PokemonViewModel
import com.example.pokemon_mvvm_roomdb.databinding.FragmentPokedexBinding

class PokedexFragment : Fragment() {

    private lateinit var binding: FragmentPokedexBinding
    private lateinit var viewModel: PokemonViewModel
    private lateinit var pokemonFavAdapter: PokemonFavAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this)[PokemonViewModel::class.java]
        pokemonFavAdapter = PokemonFavAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokedexBinding.inflate(layoutInflater)

        return (binding.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        //pokemonClicked()
        prepareRecyclerView()
    }

    private fun observeLiveData() {
        viewModel.favPokemon.observe(viewLifecycleOwner, Observer { meals ->
            if (meals.isEmpty()) {
                // Show a toast message indicating that the list is empty
                Toast.makeText(context, "EMPTY", Toast.LENGTH_SHORT)
                //binding.tvEmpty.visibility = View.VISIBLE
            } else {
                meals.forEach {
                    pokemonFavAdapter.setFavorites(list = meals as ArrayList<PokemonDetails>)
                }
                //binding.tvEmpty.visibility = View.GONE
            }
            //binding.layoutLoading.visibility = View.GONE

        })
    }

    private fun prepareRecyclerView() {
        binding.rvFav.apply {
            layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
            adapter = pokemonFavAdapter
        }
    }
}