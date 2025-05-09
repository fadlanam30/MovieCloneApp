package tech.fadlan.moviecloneapp.presentation.ui.home

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tech.fadlan.moviecloneapp.R
import tech.fadlan.moviecloneapp.databinding.MovieItemLayoutBinding
import tech.fadlan.moviecloneapp.domain.model.MovieItem
import tech.fadlan.moviecloneapp.utils.loadImageUrl

class MovieAdapter(
    private val onMovieClick: (movieId: Int) -> Unit
) : PagingDataAdapter<MovieItem, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(private val binding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieItem) {
            binding.apply {

                val density = itemView.context.resources.displayMetrics.density

                val heightDp = 180f
                val widthDp = heightDp * 5f / 8f

                val heightPx = (heightDp * density).toInt()
                val widthPx = (widthDp * density).toInt()
                val marginPx = (4 * density).toInt()

                val screenWidth = Resources.getSystem().displayMetrics.widthPixels
                val spanCount = 3
                val totalSpacing = screenWidth - (spanCount * widthPx)
                val sideMargin = totalSpacing / (spanCount * 2)

//                val layoutParams = ViewGroup.MarginLayoutParams(finalWidth, finalHeight)
//                layoutParams.setMargins(margin, margin, margin, margin)
                val layoutParams = binding.root.layoutParams as GridLayoutManager.LayoutParams

                if (bindingAdapterPosition == 0) {
                    layoutParams.setMargins(0, 0, 0, 0)
                    layoutParams.height = (102f * density.toInt()).toInt()
                    headerView.visibility = View.VISIBLE
                    moviePoster.visibility = View.GONE
                } else {
                    layoutParams.setMargins(sideMargin, marginPx, sideMargin, marginPx)
                    layoutParams.width = widthPx
                    layoutParams.height = heightPx
                    headerView.visibility = View.GONE
                    moviePoster.visibility = View.VISIBLE
                    Glide.with(root)
                        .load(loadImageUrl(movie.posterPath))
                        .placeholder(
                            ContextCompat.getDrawable(
                                root.context,
                                R.drawable.placeholder_image
                            )
                        )
                        .into(moviePoster)
                }

                binding.root.layoutParams = layoutParams
                if (movie.id != 0) {
                    root.setOnClickListener {
                        onMovieClick(movie.id)
                    }
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MovieItem,
                newItem: MovieItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}