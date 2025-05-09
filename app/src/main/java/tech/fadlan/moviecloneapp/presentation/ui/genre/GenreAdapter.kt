package tech.fadlan.moviecloneapp.presentation.ui.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import tech.fadlan.moviecloneapp.R
import tech.fadlan.moviecloneapp.databinding.GenreItemLayoutBinding
import tech.fadlan.moviecloneapp.domain.model.GenreItem

class GenreAdapter(
    private val selectedGenre: GenreItem,
    private val onGenreClick: (GenreItem) -> Unit,
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    private val listItem = ArrayList<GenreItem>()

    fun submitList(list: List<GenreItem>) {
        listItem.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            GenreItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = listItem[position]
        holder.bind(genre)
    }

    inner class GenreViewHolder(private val binding: GenreItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenreItem) {
            binding.genreNameTv.apply {
                text = genre.name

                if (genre == selectedGenre) {
                    textSize = 24f
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    typeface = ResourcesCompat.getFont(context, R.font.netflix_sans_bold)
                } else {
                    textSize = 18f
                    setTextColor(ContextCompat.getColor(context, R.color.accent_secondary))
                    typeface = ResourcesCompat.getFont(context, R.font.netflix_sans_regular)
                }
                binding.root.setOnClickListener {
                    onGenreClick(genre)
                }

            }
        }
    }
}