package com.example.pokemon_mvvm_roomdb.UI.fragments

import PokemonListAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.UI.BlurredBackground
import com.example.pokemon_mvvm_roomdb.data.viewmodel.PokemonViewModel
import com.example.pokemon_mvvm_roomdb.databinding.FragmentListBinding

class PokemonListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: PokemonViewModel
    private lateinit var pokemonListAdapter: PokemonListAdapter
    private lateinit var blurredBackground: BlurredBackground

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[PokemonViewModel::class.java]
        pokemonListAdapter = PokemonListAdapter()
        blurredBackground = BlurredBackground(activity)

        blurredBackground.showProgressingView()
        // Fetch the list of Pokémon
        viewModel.fetchPokemonListWithSprites()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe changes in the list of Pokémon
        observeLiveData()
        onPokemonClicked()

    }

    private fun observeLiveData() {
        binding.rvList.layoutManager = GridLayoutManager(activity, 1)
        binding.rvList.adapter = pokemonListAdapter

        viewModel.pokemonListSprite.observe(viewLifecycleOwner, Observer { pokemonList ->
            pokemonListAdapter.setData(pokemonList)
            blurredBackground.hideProgressingView()
        })


    }


    private fun onPokemonClicked() {
        pokemonListAdapter.onItemClick = { pokemon ->

            val navController = findNavController(requireActivity(), R.id.navFragmentController)
            navController.navigate(R.id.action_listFragment_to_pokemonDetailsFragment)

            val sharedPreferences =
                requireContext().getSharedPreferences("pokemon_pref", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("pokemon_id", pokemon.name).apply()
        }
    }
}
