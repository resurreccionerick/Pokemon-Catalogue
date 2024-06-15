import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon.model.details.PokemonDetails
import com.example.pokemon_mvvm_roomdb.R
import com.example.pokemon_mvvm_roomdb.databinding.PokemonItemBinding

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    lateinit var onItemClick: ((PokemonDetails) -> Unit)

    private var pokemonList = ArrayList<PokemonDetails>()

    fun setData(pokemonList: List<PokemonDetails>) {
        this.pokemonList.clear()
        this.pokemonList.addAll(pokemonList)
        notifyDataSetChanged()
    }

    class PokemonViewHolder(val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = pokemonList[position]

        holder.binding.tvPokemonName.text = item.name.toUpperCase()

        val (startColor, endColor) = getTypeGradientColors(item.types.firstOrNull()?.type?.name)
        val paint = holder.binding.tvPokemonName.paint
        val width = paint.measureText(item.name.toUpperCase())
        val textShader = LinearGradient(
            0f, 0f, width, holder.binding.tvPokemonName.textSize,
            intArrayOf(startColor, endColor),
            null,
            Shader.TileMode.CLAMP
        )
        holder.binding.tvPokemonName.paint.shader = textShader

        Glide.with(holder.itemView.context)
            .load(item.sprites.front_default)
            .into(holder.binding.pokemonImg)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(item)
        }


    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    private fun getTypeGradientColors(typeName: String?): Pair<Int, Int> {
        return when (typeName?.toLowerCase()) {
            "normal" -> Pair(Color.LTGRAY, Color.DKGRAY)
            "fire" -> Pair(Color.RED, Color.GRAY)
            "water" -> Pair(Color.BLUE, Color.CYAN)
            "electric" -> Pair(Color.YELLOW, Color.rgb(255, 165, 0)) // Orange
            "grass" -> Pair(Color.GREEN, Color.rgb(34, 139, 34)) // Forest Green
            "ice" -> Pair(Color.CYAN, Color.BLUE)
            "fighting" -> Pair(Color.rgb(156, 102, 31), Color.DKGRAY) // Brown to Dark Gray
            "poison" -> Pair(Color.rgb(128, 0, 128), Color.DKGRAY) // Purple to Dark Gray
            "flying" -> Pair(Color.LTGRAY, Color.DKGRAY)
            "psychic" -> Pair(Color.MAGENTA, Color.rgb(255, 20, 147)) // Magenta to Deep Pink
            "bug" -> Pair(Color.rgb(34, 139, 34), Color.GREEN) // Forest Green to Green
            "rock" -> Pair(Color.GRAY, Color.DKGRAY)
            "ghost" -> Pair(Color.rgb(128, 128, 128), Color.DKGRAY) // Gray to Dark Gray
            "dragon" -> Pair(
                Color.rgb(75, 0, 130),
                Color.rgb(138, 43, 226)
            ) // Indigo to Blue Violet
            "dark" -> Pair(Color.rgb(25, 25, 112), Color.BLACK) // Midnight Blue to Black
            "steel" -> Pair(Color.rgb(169, 169, 169), Color.LTGRAY) // Dark Gray to Light Gray
            "fairy" -> Pair(Color.rgb(255, 192, 203), Color.MAGENTA) // Pink to Magenta
            else -> Pair(Color.LTGRAY, Color.DKGRAY) // Default gradient colors
        }
    }
}
