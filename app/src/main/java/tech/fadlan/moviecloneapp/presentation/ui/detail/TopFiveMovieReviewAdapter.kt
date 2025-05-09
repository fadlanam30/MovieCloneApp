package tech.fadlan.moviecloneapp.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import tech.fadlan.moviecloneapp.databinding.ReviewItemLayoutBinding
import tech.fadlan.moviecloneapp.domain.model.ReviewItem
import tech.fadlan.moviecloneapp.utils.ellipsize
import tech.fadlan.moviecloneapp.utils.formatHtml
import java.util.Locale

class TopFiveMovieReviewAdapter(
    private val onReviewClick: (review: ReviewItem) -> Unit
) : RecyclerView.Adapter<TopFiveMovieReviewAdapter.PreviewMovieReviewViewHolder>() {

    private val listItem = ArrayList<ReviewItem>()

    fun submitList(list: List<ReviewItem>) {
        listItem.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PreviewMovieReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ReviewItemLayoutBinding.inflate(layoutInflater, parent, false)
        return PreviewMovieReviewViewHolder(binding)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: PreviewMovieReviewViewHolder, position: Int) {
        val review = listItem[position]
        holder.bind(review)
    }

    inner class PreviewMovieReviewViewHolder(
        private val binding: ReviewItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewItem) {
            binding.apply {
                tvAuthor.text = review.author.ellipsize()
                tvRating.text = String.format(Locale.US, "%.1f", review.authorDetails.rating)
                tvContentReview.text = review.content.formatHtml()

                tvAuthor.isVisible = review.id != ""
                ratingLayout.isVisible = review.id != "" && review.authorDetails.rating > 0.0
                tvContentReview.isVisible = review.id != ""
                btnNavRate.isVisible = review.id != ""

                btnLoadMore.isVisible = review.id == ""

                btnLoadMore.setOnClickListener {
                    onReviewClick(review)
                }

                if (review.id != "") {
                    root.setOnClickListener {
                        onReviewClick(review)
                    }
                }
            }
        }
    }

}
