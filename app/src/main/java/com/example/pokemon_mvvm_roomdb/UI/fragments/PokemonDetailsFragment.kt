package com.example.pokemon_mvvm_roomdb.UI.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.bumptech.glide.Glide
import com.example.pokemon.model.details.Ability
import com.example.pokemon.model.details.Move
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon.model.details.Sprites
import com.example.pokemon.model.details.Stat
import com.example.pokemon.model.details.Type
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.UI.BlurredBackground
import com.example.pokemon_mvvm_roomdb.databinding.FragmentPokemonDetailsBinding
import com.example.pokemon_mvvm_roomdb.data.viewmodel.PokemonViewModel

class PokemonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailsBinding
    private lateinit var viewModel: PokemonViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var blurredBackground: BlurredBackground
    private var pokemonFav: List<PokemonDetails>? = null // Variable to hold favorite Pokemon list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blurredBackground = BlurredBackground(requireActivity())
        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        sharedPreferences =
            requireContext().getSharedPreferences("pokemon_pref", Context.MODE_PRIVATE)

        // Fetch the list of Pokémon
        viewModel.fetchPokemonListWithSprites()

        // Observe the favorite Pokemon list
        viewModel.favPokemon.observe(this, Observer { favPokemonList ->
            pokemonFav = favPokemonList // Update the favorite Pokemon list
            updateFavoriteButton() // Update UI button text
        })

        blurredBackground.showProgressingView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the Pokemon ID from SharedPreferences
        val pokemonName = sharedPreferences.getString("pokemon_id", null)

        // Fetch the selected Pokémon details
        if (pokemonName != null) {
            viewModel.fetchPokemon(pokemonName)
        }

        // Set up click listeners
        setupClickListeners()

        // Observe changes in the selected Pokémon details
        observePokemonDetails()
    }

    private fun setupClickListeners() {
        binding.btnAddToFavorites.setOnClickListener {
            viewModel.pokemon.value?.let { pokemonDetails ->
                // Add or remove from favorites based on current status
                if (pokemonFav?.any { it.name == pokemonDetails.name } == true) {
                    viewModel.deletePokemon(pokemonDetails)
                    Toast.makeText(
                        requireContext(),
                        "${pokemonDetails.name} removed from favorites!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.insertPokemon(pokemonDetails)
                    Toast.makeText(
                        requireContext(),
                        "${pokemonDetails.name} added to favorites!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btnBack.btnBack.setOnClickListener {
            findNavController(requireActivity(), R.id.navFragmentController)
                .navigate(R.id.action_pokemonDetailsFragment_to_listFragment)
        }
    }

    private fun observePokemonDetails() {
        viewModel.pokemon.observe(viewLifecycleOwner, Observer { pokemonDetails ->
            pokemonDetails?.let { pokemon ->
                updatePokemonUI(pokemon)
                updateFavoriteButton()
            }
        })

        viewModel.pokemonListSprite.observe(
            viewLifecycleOwner,
            Observer { pokemonListSpriteDetails ->
                viewModel.pokemon.value?.let { pokemon ->
                    updateFavoriteButton()
                }
            })

        blurredBackground.hideProgressingView()
    }

    private fun updatePokemonUI(pokemonDetails: PokemonDetails) {
        binding.nameTextView.text = pokemonDetails.name
        binding.nameTextView.isAllCaps = true
        binding.heightTextView.text = "Height: ${pokemonDetails.height}"
        binding.weightTextView.text = "Weight: ${pokemonDetails.weight}"

        val backgroundColor = getTypeBackgroundColor(pokemonDetails.types.firstOrNull()?.type?.name)
        binding.llBackground.setBackgroundColor(backgroundColor)

        bindAbilities(pokemonDetails.abilities)
        bindTypes(pokemonDetails.types)
        bindMoves(pokemonDetails.moves)
        bindSprites(pokemonDetails.sprites)
        bindStats(pokemonDetails.stats)
    }

    private fun updateFavoriteButton() {
        val pokemonDetails = viewModel.pokemon.value
        val pokemonFav = pokemonFav

        if (pokemonDetails != null && pokemonFav != null) {
            if (pokemonFav.any { it.name == pokemonDetails.name }) {
                binding.btnAddToFavorites.text = "Remove from favorite"
                binding.btnAddToFavorites.setBackgroundColor(Color.RED)
            } else {
                binding.btnAddToFavorites.text = "Add to favorite"
                // Set default background color if needed
                // binding.btnAddToFavorites.setBackgroundColor(DEFAULT_COLOR)
            }
        } else {
            binding.btnAddToFavorites.text = "Add to favorite"
            // Set default background color if needed
            // binding.btnAddToFavorites.setBackgroundColor(DEFAULT_COLOR)
        }
        binding.btnAddToFavorites.visibility = View.VISIBLE
    }

    private fun getTypeBackgroundColor(typeName: String?): Int {
        return when (typeName?.toLowerCase()) {
            "normal" -> Color.LTGRAY
            "fire" -> Color.RED
            "water" -> Color.BLUE
            "electric" -> Color.YELLOW
            "grass" -> Color.GREEN
            "ice" -> Color.CYAN
            "fighting", "ground" -> Color.rgb(156, 102, 31) // Brown
            "poison" -> Color.rgb(128, 0, 128) // Purple
            "flying" -> Color.LTGRAY
            "psychic" -> Color.MAGENTA
            "bug" -> Color.rgb(0, 128, 0) // Dark green
            "rock" -> Color.GRAY
            "ghost" -> Color.rgb(128, 128, 128) // Gray
            "dragon" -> Color.rgb(75, 0, 130) // Indigo
            "dark" -> Color.rgb(25, 25, 112) // Midnight blue
            "steel" -> Color.rgb(169, 169, 169) // Dark gray
            "fairy" -> Color.rgb(255, 192, 203) // Pink
            else -> Color.TRANSPARENT
        }
    }

    private fun bindAbilities(abilities: List<Ability>) {
        val abilitiesLayout = binding.abilitiesLayout
        abilitiesLayout.removeAllViews()

        abilities.forEach { ability ->
            val textView = TextView(requireContext())
            textView.text = ability.ability.name
            textView.textSize = 16f
            textView.setTextColor(Color.BLACK)
            textView.setPadding(0, 0, 0, 0)
            abilitiesLayout.addView(textView)
        }
    }

    private fun bindTypes(types: List<Type>) {
        val typesLayout = binding.typesLayout

        types.forEach { type ->
            val textView = TextView(requireContext())
            textView.text = type.type.name
            textView.textSize = 16f
            textView.setTextColor(Color.BLACK)
            textView.setPadding(0, 0, 0, 0)
            typesLayout.addView(textView)
        }
    }

    private fun bindMoves(moves: List<Move>) {
        val movesLayout = binding.movesLayout
        movesLayout.removeAllViews()

        moves.forEach { move ->
            val textView = TextView(requireContext())
            textView.text = move.move.name
            textView.textSize = 16f
            movesLayout.addView(textView)
        }
    }

    private fun bindSprites(sprites: Sprites?) {
        sprites?.apply {
            Glide.with(requireContext())
                .load(front_default)
                .into(binding.frontSpriteImageView)

            Glide.with(requireContext())
                .load(back_default)
                .into(binding.backSpriteImageView)
        }
    }

    private fun bindStats(stats: List<Stat>) {
        val statsLayout = binding.statsLayout
        statsLayout.removeAllViews()

        stats.forEach { stat ->
            val progressBar =
                ProgressBar(requireContext(), null, android.R.attr.progressBarStyleHorizontal)
            progressBar.max = 100
            progressBar.progress = stat.base_stat

            progressBar.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val textView = TextView(requireContext())
            textView.text = "${stat.stat.name}: Base - ${stat.base_stat}, Effort - ${stat.effort}"
            textView.textSize = 16f

            val linearLayout = LinearLayout(requireContext())
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.addView(progressBar)
            linearLayout.addView(textView)

            statsLayout.addView(linearLayout)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        blurredBackground.hideProgressingView()
    }
}
