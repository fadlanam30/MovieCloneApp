package tech.fadlan.moviecloneapp.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tech.fadlan.moviecloneapp.R
import tech.fadlan.moviecloneapp.databinding.FragmentHomeBinding
import tech.fadlan.moviecloneapp.presentation.common.MovieLoadStateAdapter
import tech.fadlan.moviecloneapp.presentation.common.collectFlow
import tech.fadlan.moviecloneapp.presentation.ui.genre.GenreDialogFragment

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(
            onMovieClick = { movieId ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(movieId)
                )
            }
        )

        setupRecyclerView()

        binding.cancelCategoryButton.setOnClickListener {
            viewModel.resetSelectedGenre()
        }

        collectFlow(
            flow = viewModel.selectedGenre,
            onCollectData = { selectedGenre ->
                binding.selectedGenreTv.text =
                    if (selectedGenre.name != "") selectedGenre.name else resources.getString(R.string.category)

                binding.cancelCategoryButton.isVisible = selectedGenre.name != ""

                binding.selectedGenreTv.background =
                    if (selectedGenre.name != "")
                        ContextCompat.getDrawable(requireContext(), R.drawable.fill_background)
                    else
                        ContextCompat.getDrawable(requireContext(), R.drawable.outline_background)

                binding.selectedGenreTv.setOnClickListener {
                    GenreDialogFragment(
                        selectedGenreParameter = selectedGenre,
                        onClickGenre = { genre ->
                            viewModel.onSelectedGenre(genre)
                        }
                    ).show(childFragmentManager, "GenreDialogFragment")
                }
            }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movies.collectLatest {
                        movieAdapter.submitData(it)
                    }
                }

                launch {
                    movieAdapter.loadStateFlow.collectLatest { loadStates ->
                        val isLoading = loadStates.refresh is LoadState.Loading
                        val isError = loadStates.refresh is LoadState.Error
                        val isEmpty = loadStates.refresh is LoadState.NotLoading &&
                                movieAdapter.itemCount == 0

                        binding.progressBar.isVisible = isLoading
                        binding.tvErrorLoad.isVisible = isError || isEmpty
                        binding.btnRetry.isVisible = isError || isEmpty
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val concatAdapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )

        val myLayoutManager = GridLayoutManager(requireContext(), 3)

        binding.rvMovies.apply {
            layoutManager = myLayoutManager
            setHasFixedSize(true)
            adapter = concatAdapter
//            viewTreeObserver.addOnScrollChangedListener {
//                val scrollY = binding.rvMovies.computeVerticalScrollOffset()
//
//                if (scrollY > 200) {
//                    binding.fabScrollToTop.visibility = View.VISIBLE
//                } else {
//                    binding.fabScrollToTop.visibility = View.GONE
//                }
//
//            }

        }

        binding.btnRetry.setOnClickListener {
            movieAdapter.retry()
        }

        myLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 3 else 1
            }
        }
    }
}
