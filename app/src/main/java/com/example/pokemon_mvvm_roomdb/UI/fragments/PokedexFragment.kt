package com.example.pokemon_mvvm_roomdb.UI.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.UI.adapter.PokemonFavAdapter
import com.example.pokemon_mvvm_roomdb.data.viewmodel.PokemonViewModel
import com.example.pokemon_mvvm_roomdb.databinding.FragmentPokedexBinding
import com.google.android.material.snackbar.Snackbar

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
        pokemonClicked()
        prepareRecyclerView()

        binding.customToolbar.toolbarTitle.text = "Favorites"
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

    private fun pokemonClicked() {
        pokemonFavAdapter.onItemClick = { pokemon ->
            val navController = findNavController(requireActivity(), R.id.navFragmentController)
            navController.navigate(R.id.action_pokedexFragment_to_pokemonDetailsFragment)

            val sharedPreferences =
                requireContext().getSharedPreferences("pokemon_pref", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("pokemon_id", pokemon.name).apply()
        }

        pokemonFavAdapter.onDeleteItemClick = { pokemon ->
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Favorite")
                .setMessage("Are you sure you want to delete this favorite?")
                .setPositiveButton("Delete") { dialogInterface: DialogInterface, _: Int ->
                    viewModel.deletePokemon(pokemon)
                    Snackbar.make(requireView(), "Favorite deleted", Snackbar.LENGTH_LONG)
                        .setAction(
                            "Undo"
                        ) {
                            viewModel.insertPokemon(pokemon)
                            pokemonFavAdapter.notifyItemInserted(pokemon.id)
                        }.show()
                    dialogInterface.dismiss()
                }
                .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }
                .show()
        }
    }

}