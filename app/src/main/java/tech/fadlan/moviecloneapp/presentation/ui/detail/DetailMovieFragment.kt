package tech.fadlan.moviecloneapp.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import tech.fadlan.moviecloneapp.R
import tech.fadlan.moviecloneapp.databinding.FragmentDetailMovieBinding
import tech.fadlan.moviecloneapp.domain.model.MovieDetail
import tech.fadlan.moviecloneapp.presentation.common.collectUiState
import tech.fadlan.moviecloneapp.presentation.common.registerOnBackPressHandler
import java.util.Locale

@AndroidEntryPoint
class DetailMovieFragment : Fragment(R.layout.fragment_detail_movie) {

    private val binding: FragmentDetailMovieBinding by viewBinding(FragmentDetailMovieBinding::bind)

    private val viewModel: DetailMovieViewModel by viewModels()

    private lateinit var topFiveMovieReviewAdapter: TopFiveMovieReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerOnBackPressHandler()

        val movieId = requireArguments().getInt("movieId")

        viewModel.getMovieDetail(movieId)
        viewModel.getTopFiveMovieReviews(movieId)
        topFiveMovieReviewAdapter = TopFiveMovieReviewAdapter(
            onReviewClick = { review ->
                if (review.id != "") {
                    findNavController().navigate(
                        DetailMovieFragmentDirections.actionDetailMovieFragmentToDetailReviewFragment(
                            binding.tvTitle.text.toString(),
                            review,
                        )
                    )
                } else {
                    findNavController().navigate(
                        DetailMovieFragmentDirections.actionDetailMovieFragmentToReviewMovieFragment(
                            movieId,
                            binding.tvTitle.text.toString()
                        )
                    )
                }
            }
        )
        setupTopFiveMovieRecyclerView()


        collectUiState(
            flow = viewModel.detailMovie,
            onSuccess = { movieDetail ->
                binding.progressBar.visibility = View.GONE
                setupDataDetail(movieDetail)
                viewModel.getMovieVideos(movieId)
                binding.contentLayout.visibility = View.VISIBLE
                binding.tvErrorLoad.visibility = View.GONE
                binding.btnRetry.visibility = View.GONE


            },
            onLoading = {
                binding.progressBar.visibility = View.VISIBLE
                binding.contentLayout.visibility = View.GONE
                binding.tvErrorLoad.visibility = View.GONE
                binding.btnRetry.visibility = View.GONE
            },
            onError = {
                binding.progressBar.visibility = View.GONE
                binding.contentLayout.visibility = View.GONE
                binding.tvErrorLoad.visibility = View.VISIBLE
                binding.btnRetry.visibility = View.VISIBLE
            }
        )

        collectUiState(
            flow = viewModel.topFiveMovieReviews,
            onSuccess = { reviews ->
                topFiveMovieReviewAdapter.submitList(reviews)

                binding.tvEmptyReview.isVisible = reviews.isEmpty()

                binding.progressBar.visibility = View.GONE
                binding.tvTitleReview.visibility = View.VISIBLE
            },
            onLoading = {
                binding.progressBar.visibility = View.VISIBLE
                binding.tvTitleReview.visibility = View.GONE

            },
            onError = {
                binding.progressBar.visibility = View.GONE
                binding.tvTitleReview.visibility = View.GONE
            }
        )

        lifecycle.addObserver(binding.youtubePlayerView)

        collectUiState(
            flow = viewModel.movieVideo,
            onSuccess = { videos ->
                binding.youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadVideo(videos.key, 0f)
                    }
                })
                binding.progressBar.visibility = View.GONE
            },
            onLoading = {
                binding.progressBar.visibility = View.VISIBLE
            },
            onError = {
                binding.progressBar.visibility = View.GONE
            }
        )

        binding.btnRetry.setOnClickListener {
            viewModel.getMovieDetail(movieId)
            viewModel.getTopFiveMovieReviews(movieId)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setupTopFiveMovieRecyclerView() {
        binding.rvTopFiveMovieReviews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = topFiveMovieReviewAdapter
        }
    }

    private fun setupDataDetail(movieDetail: MovieDetail) {
        binding.apply {
            tvTitle.text = movieDetail.title
            tvYearRelease.text = String.format(Locale.US, "%d", movieDetail.releaseLocalDate.year)
            tvOverview.text = movieDetail.overview
            tvDuration.text = movieDetail.durationString
            tvDuration.isVisible = movieDetail.durationString != "0h 0m"
            tvRating.text = String.format(Locale.US, "%.1f", movieDetail.voteAverage)
            tvRatingCount.text = String.format(Locale.US, "(%s)", movieDetail.voteCountString)
            tvAdult.text = if (movieDetail.adult) "18+" else "13+"

        }
    }
}