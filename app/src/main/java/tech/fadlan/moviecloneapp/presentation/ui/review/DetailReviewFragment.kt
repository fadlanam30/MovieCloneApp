package tech.fadlan.moviecloneapp.presentation.ui.review

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import tech.fadlan.moviecloneapp.R
import tech.fadlan.moviecloneapp.databinding.FragmentDetailReviewBinding
import tech.fadlan.moviecloneapp.domain.model.ReviewItem
import tech.fadlan.moviecloneapp.presentation.common.registerOnBackPressHandler
import tech.fadlan.moviecloneapp.utils.formatHtml
import java.util.Locale

class DetailReviewFragment : Fragment(R.layout.fragment_detail_review) {

    private val binding: FragmentDetailReviewBinding by viewBinding(FragmentDetailReviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerOnBackPressHandler()

        val movieName = requireArguments().getString("movieName")
        val movieReview = requireArguments().getParcelable<ReviewItem>("movieReview")

        binding.apply {
            tvTitleMovie.text = movieName
            if (movieReview != null) {
                tvTitleReview.text = getString(
                    R.string.review_written_by,
                    movieReview.author,
                    movieReview.createdAtString
                )
                tvContentReview.text = movieReview.content.formatHtml()
                ratingLayout.isVisible = movieReview.authorDetails.rating > 0.0
                tvRating.text = String.format(Locale.US, "%.1f", movieReview.authorDetails.rating)
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

}