package com.example.pokemon_mvvm_roomdb.UI.fragments

import PokemonListAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.UI.BlurredBackground
import com.example.pokemon_mvvm_roomdb.data.viewmodel.PokemonViewModel
import com.example.pokemon_mvvm_roomdb.databinding.FragmentListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: PokemonViewModel
    private lateinit var pokemonListAdapter: PokemonListAdapter
    private lateinit var blurredBackground: BlurredBackground

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        pokemonListAdapter = PokemonListAdapter()

        blurredBackground = BlurredBackground(activity)

        // Fetch the list of Pokémon
        viewModel.fetchPokemonListWithSprites()
        blurredBackground.showProgressingView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()
        return binding.root
    }


    private fun setupRecyclerView() {
        binding.rvList.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rvList.adapter = pokemonListAdapter

        pokemonListAdapter.onItemClick = { pokemon ->
            val sharedPreferences =
                requireContext().getSharedPreferences("pokemon_pref", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("pokemon_id", pokemon.name).apply()

            findNavController().navigate(R.id.action_listFragment_to_pokemonDetailsFragment)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pokemonList.collectLatest { pagingData ->
                Log.d("PokemonListFragment", "Paging data received: $pagingData")
                pokemonListAdapter.submitData(pagingData)
                //blurredBackground.hideProgressingView()
            }
        }

        viewModel.pokemonListSpriteLiveData.observe(viewLifecycleOwner) { pokemonList ->
            Log.d("PokemonListFragment", "Live data received: $pokemonList")
            pokemonListAdapter.setData(pokemonList)
            blurredBackground.hideProgressingView()
        }
    }
}
