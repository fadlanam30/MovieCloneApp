package tech.fadlan.moviecloneapp.presentation.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import tech.fadlan.moviecloneapp.data.remote.MovieReviewsPagingSource
import tech.fadlan.moviecloneapp.domain.model.ReviewItem
import tech.fadlan.moviecloneapp.domain.repository.TMDBRepository
import javax.inject.Inject

@HiltViewModel
class ReviewMovieViewModel @Inject constructor(
    private val repository: TMDBRepository,
) : ViewModel() {

    fun getReviews(movieId: Int): Flow<PagingData<ReviewItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MovieReviewsPagingSource(repository, movieId)
            }
        ).flow.cachedIn(viewModelScope)
    }

}