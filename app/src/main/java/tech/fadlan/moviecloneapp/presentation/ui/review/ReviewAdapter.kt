package tech.fadlan.moviecloneapp.presentation.ui.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.fadlan.moviecloneapp.databinding.ReviewItemLayoutBinding
import tech.fadlan.moviecloneapp.domain.model.ReviewItem
import tech.fadlan.moviecloneapp.utils.ellipsize
import tech.fadlan.moviecloneapp.utils.formatHtml
import java.util.Locale

class ReviewAdapter(
    private val onReviewClick: (review: ReviewItem) -> Unit
) : PagingDataAdapter<ReviewItem, ReviewAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ReviewItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(private val binding: ReviewItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewItem) {

            binding.apply {
                tvAuthor.text = review.author.ellipsize()
                ratingLayout.isVisible = review.authorDetails.rating > 0.0
                tvRating.text = String.format(Locale.US, "%.1f", review.authorDetails.rating)
                tvContentReview.text = review.content.formatHtml()

                root.setOnClickListener {
                    onReviewClick(review)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewItem>() {
            override fun areItemsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ReviewItem,
                newItem: ReviewItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}