package tech.fadlan.moviecloneapp.presentation.ui.review

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tech.fadlan.moviecloneapp.R
import tech.fadlan.moviecloneapp.databinding.FragmentReviewMovieBinding
import tech.fadlan.moviecloneapp.presentation.common.MovieLoadStateAdapter
import tech.fadlan.moviecloneapp.presentation.common.registerOnBackPressHandler
import tech.fadlan.moviecloneapp.presentation.ui.detail.DetailMovieFragmentDirections

@AndroidEntryPoint
class ReviewMovieFragment : Fragment(R.layout.fragment_review_movie) {

    private val binding: FragmentReviewMovieBinding by viewBinding(FragmentReviewMovieBinding::bind)

    private val viewModel: ReviewMovieViewModel by viewModels()

    private lateinit var reviewAdapter: ReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerOnBackPressHandler()

        val movieId = arguments?.getInt("movieId") ?: 0
        val movieName = arguments?.getString("movieName") ?: ""

        reviewAdapter = ReviewAdapter(
            onReviewClick = { review ->
                DetailMovieFragmentDirections.actionDetailMovieFragmentToDetailReviewFragment(
                    movieName,
                    review,
                )
            }
        )

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.getReviews(movieId).collectLatest {
                        reviewAdapter.submitData(it)
                    }
                }

                launch {
                    reviewAdapter.loadStateFlow.collectLatest { loadStates ->
                        val isLoading = loadStates.refresh is LoadState.Loading
                        val isError = loadStates.refresh is LoadState.Error
                        val isEmpty =
                            loadStates.refresh is LoadState.NotLoading && reviewAdapter.itemCount == 0

                        val itemCount = reviewAdapter.itemCount

                        binding.progressBar.isVisible = isLoading
                        binding.tvErrorLoad.isVisible = isError || isEmpty
                        binding.btnRetry.isVisible = isError
                    }
                }
            }
        }
        binding.tvTitleMovie.text = movieName

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {

        val concatAdapter = reviewAdapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter { reviewAdapter.retry() }
        )

        binding.rvMovieReviews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = concatAdapter
        }

        binding.btnRetry.setOnClickListener {
            reviewAdapter.retry()
        }
    }
}