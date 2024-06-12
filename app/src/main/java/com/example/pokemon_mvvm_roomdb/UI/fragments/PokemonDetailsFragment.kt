package com.example.pokemon_mvvm_roomdb.UI.fragments

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.pokemon.model.details.Ability
import com.example.pokemon.model.details.Move
import com.example.pokemon.model.details.Sprites
import com.example.pokemon.model.details.Stat
import com.example.pokemon.model.details.Type
import com.example.pokemon_mvvm_roomdb.data.viewmodel.PokemonViewModel
import com.example.pokemon_mvvm_roomdb.databinding.FragmentPokemonDetailsBinding

class PokemonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailsBinding
    private lateinit var viewModel: PokemonViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[PokemonViewModel::class.java]
        sharedPreferences =
            requireContext().getSharedPreferences("pokemon_pref", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the Pokemon ID from SharedPreferences
        val pokemonName = sharedPreferences.getString("pokemon_id", null)

        // Fetch the list of Pokémon
        if (pokemonName != null) {
            viewModel.fetchPokemon(pokemonName)
        }

        // Observe changes in the list of Pokémon
        observeLiveData()


    }


    private fun observeLiveData() {
        viewModel.pokemon.observe(viewLifecycleOwner, Observer { pokemonDetails ->
            pokemonDetails?.let {
                // Bind data to TextViews
                binding.nameTextView.text = "${pokemonDetails.name}"
                binding.nameTextView.isAllCaps = true
                binding.heightTextView.text = "Height: ${pokemonDetails.height}"
                binding.weightTextView.text = "Weight: ${pokemonDetails.weight}"

                // Determine background color based on the primary type
                val backgroundColor =
                    getTypeBackgroundColor(pokemonDetails.types.firstOrNull()?.type?.name)

                // Set background color
                binding.llBackground.setBackgroundColor(backgroundColor)

                // Bind Abilities
                bindAbilities(pokemonDetails.abilities)

                // Bind Types
                bindTypes(pokemonDetails.types)

                // Bind Moves
                bindMoves(pokemonDetails.moves)

                // Bind Sprites
                bindSprites(pokemonDetails.sprites)

                //Bind Stats
                bindStats(pokemonDetails.stats)
            }
        })
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
        abilitiesLayout.removeAllViews() // Clear any existing views

        abilities.forEach { ability ->
            val textView = TextView(requireContext())
            textView.text = ability.ability.name
            textView.textSize = 16f
            textView.setTextColor(Color.BLACK)
            textView.setPadding(0, 0, 0, 0) // Add padding between items
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
            textView.setPadding(0, 0, 0, 0) // Add padding between items
            typesLayout.addView(textView)
        }
    }

    private fun bindMoves(moves: List<Move>) {
        val movesLayout = binding.movesLayout

        moves.forEach { move ->
            val textView = TextView(requireContext())
            textView.text = move.move.name
            textView.textSize = 16f
            movesLayout.addView(textView)
        }
    }

    private fun bindSprites(sprites: Sprites?) {
        sprites?.apply {
            // Load front sprite
            front_default?.let {
                Glide.with(requireContext())
                    .load(it)
                    .into(binding.frontSpriteImageView)
            }

            // Load back sprite
            back_default?.let {
                Glide.with(requireContext())
                    .load(it)
                    .into(binding.backSpriteImageView)
            }
        }
    }

    private fun bindStats(stats: List<Stat>) {
        val statsLayout = binding.statsLayout

        stats.forEach { stat ->
            val progressBar = ProgressBar(requireContext(), null, R.attr.progressBarStyleHorizontal)
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


}
