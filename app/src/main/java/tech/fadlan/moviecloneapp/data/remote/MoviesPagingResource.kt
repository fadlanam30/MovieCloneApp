package tech.fadlan.moviecloneapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tech.fadlan.moviecloneapp.data.response.MovieItemResponse
import tech.fadlan.moviecloneapp.data.response.asExternalModel
import tech.fadlan.moviecloneapp.domain.model.MovieItem
import tech.fadlan.moviecloneapp.domain.repository.TMDBRepository

class MoviesPagingSource(
    private val repository: TMDBRepository,
    private val title: String = "",
    private val genresIds: String = "",
) : PagingSource<Int, MovieItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        val page = params.key ?: 1
        return try {
            val response = repository.discoverMovies(page = page, title = title, genresIds = genresIds)
            val updatedResults = listOf(MovieItemResponse.DEFAULT) + response.results
            val updatedResponse = response.copy(results = updatedResults)
            LoadResult.Page(
                data = updatedResponse.results.map(MovieItemResponse::asExternalModel),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.page < response.totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}