package tech.fadlan.moviecloneapp.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.fadlan.moviecloneapp.core.Result
import tech.fadlan.moviecloneapp.data.response.DiscoverMovieResponse
import tech.fadlan.moviecloneapp.data.response.ReviewResponse
import tech.fadlan.moviecloneapp.domain.model.GenreItem
import tech.fadlan.moviecloneapp.domain.model.MovieDetail
import tech.fadlan.moviecloneapp.domain.model.MovieVideoItem
import tech.fadlan.moviecloneapp.domain.model.ReviewItem

interface TMDBRepository {

    suspend fun discoverMovies(page: Int, title: String, genresIds: String): DiscoverMovieResponse

    fun getGenresMovies(): Flow<Result<List<GenreItem>>>

    fun getMovieDetail(movieId: Int): Flow<Result<MovieDetail>>

    fun getMovieVideos(movieId: Int): Flow<Result<MovieVideoItem>>

    fun getTopFiveMovieReviews(movieId: Int): Flow<Result<List<ReviewItem>>>

    suspend fun getMovieReviews(page: Int,movieId: Int): ReviewResponse

}