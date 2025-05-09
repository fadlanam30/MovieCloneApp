package tech.fadlan.moviecloneapp.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tech.fadlan.moviecloneapp.core.Result
import tech.fadlan.moviecloneapp.domain.model.MovieDetail
import tech.fadlan.moviecloneapp.domain.model.MovieVideoItem
import tech.fadlan.moviecloneapp.domain.model.ReviewItem
import tech.fadlan.moviecloneapp.domain.repository.TMDBRepository
import tech.fadlan.moviecloneapp.presentation.common.UiState
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val repository: TMDBRepository,
) : ViewModel() {

    private val _detailMovie = MutableStateFlow<UiState<MovieDetail>>(UiState.Loading)
    val detailMovie: StateFlow<UiState<MovieDetail>> get() = _detailMovie

    private val _movieVideo = MutableStateFlow<UiState<MovieVideoItem>>(UiState.Loading)
    val movieVideo: StateFlow<UiState<MovieVideoItem>> get() = _movieVideo

    private val _topFiveMovieReviews = MutableStateFlow<UiState<List<ReviewItem>>>(UiState.Loading)
    val topFiveMovieReviews: StateFlow<UiState<List<ReviewItem>>> get() = _topFiveMovieReviews

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        _detailMovie.value = UiState.Loading
        repository.getMovieDetail(movieId).collect {
            _detailMovie.value = when (it) {
                is Result.Success -> UiState.Success(it.data)
                is Result.Error -> UiState.Error(it.exception)
            }
        }
    }

    fun getMovieVideos(movieId: Int) = viewModelScope.launch {
        _movieVideo.value = UiState.Loading
        repository.getMovieVideos(movieId).collect {
            _movieVideo.value = when (it) {
                is Result.Success -> UiState.Success(it.data)
                is Result.Error -> UiState.Error(it.exception)
            }
        }
    }

    fun getTopFiveMovieReviews(movieId: Int) = viewModelScope.launch {
        _topFiveMovieReviews.value = UiState.Loading
        repository.getTopFiveMovieReviews(movieId).collect {
            _topFiveMovieReviews.value = when (it) {
                is Result.Success -> {
                    UiState.Success(it.data)
                }

                is Result.Error -> UiState.Error(it.exception)
            }
        }
    }
}