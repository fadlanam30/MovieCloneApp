package tech.fadlan.moviecloneapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tech.fadlan.moviecloneapp.data.response.ReviewItemResponse
import tech.fadlan.moviecloneapp.data.response.asExternalModel
import tech.fadlan.moviecloneapp.domain.model.ReviewItem
import tech.fadlan.moviecloneapp.domain.repository.TMDBRepository

class MovieReviewsPagingSource(
    private val repository: TMDBRepository,
    private val movieId: Int,
) : PagingSource<Int, ReviewItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewItem> {
        val page = params.key ?: 1
        return try {
            val response = repository.getMovieReviews(page = page, movieId = movieId)
            LoadResult.Page(
                data = response.results.map(ReviewItemResponse::asExternalModel),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.page < response.totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ReviewItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}