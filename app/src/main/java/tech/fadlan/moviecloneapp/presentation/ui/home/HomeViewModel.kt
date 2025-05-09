package tech.fadlan.moviecloneapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import tech.fadlan.moviecloneapp.core.Result
import tech.fadlan.moviecloneapp.data.remote.MoviesPagingSource
import tech.fadlan.moviecloneapp.domain.model.GenreItem
import tech.fadlan.moviecloneapp.domain.model.MovieItem
import tech.fadlan.moviecloneapp.domain.repository.TMDBRepository
import tech.fadlan.moviecloneapp.presentation.common.UiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TMDBRepository,
) : ViewModel() {

    private val _genres = MutableStateFlow<UiState<List<GenreItem>>>(UiState.Loading)
    val genres: StateFlow<UiState<List<GenreItem>>> get() = _genres

    private val _selectedGenre = MutableStateFlow(GenreItem())
    val selectedGenre: StateFlow<GenreItem> get() = _selectedGenre

    init {
        getGenresMovies()
    }

    private fun getGenresMovies() = viewModelScope.launch {
        _genres.value = UiState.Loading
        repository.getGenresMovies().collect {
            _genres.value = when (it) {
                is Result.Success -> UiState.Success(it.data)
                is Result.Error -> UiState.Error(it.exception)
            }
        }
    }

    fun onSelectedGenre(genre: GenreItem) {
        _selectedGenre.value = genre
    }

    fun resetSelectedGenre() {
        _selectedGenre.value = GenreItem()
    }

    val movies: Flow<PagingData<MovieItem>> = selectedGenre.flatMapLatest { genre ->
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviesPagingSource(
                    repository,
                    genresIds = if (genre.id == 0) "" else genre.id.toString()
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

}