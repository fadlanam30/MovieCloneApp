package tech.fadlan.moviecloneapp.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import tech.fadlan.moviecloneapp.core.IoDispatcher
import tech.fadlan.moviecloneapp.core.Result
import tech.fadlan.moviecloneapp.data.remote.TMDBService
import tech.fadlan.moviecloneapp.data.response.DiscoverMovieResponse
import tech.fadlan.moviecloneapp.data.response.GenreItemResponse
import tech.fadlan.moviecloneapp.data.response.ReviewItemResponse
import tech.fadlan.moviecloneapp.data.response.ReviewResponse
import tech.fadlan.moviecloneapp.data.response.asExternalModel
import tech.fadlan.moviecloneapp.domain.model.GenreItem
import tech.fadlan.moviecloneapp.domain.model.MovieDetail
import tech.fadlan.moviecloneapp.domain.model.MovieVideoItem
import tech.fadlan.moviecloneapp.domain.model.ReviewItem
import tech.fadlan.moviecloneapp.domain.repository.TMDBRepository
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val tmdbService: TMDBService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : TMDBRepository {

    override suspend fun discoverMovies(
        page: Int,
        title: String,
        genresIds: String
    ): DiscoverMovieResponse = tmdbService.discoverMovies(page, title, genresIds)

    override fun getGenresMovies(): Flow<Result<List<GenreItem>>> =
        flow {
            try {
                val response = tmdbService.getGenresMovies().genres
                emit(Result.Success(response.map(GenreItemResponse::asExternalModel)))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.flowOn(dispatcher)

    override fun getMovieDetail(movieId: Int): Flow<Result<MovieDetail>> =
        flow {
            try {
                val response = tmdbService.getMovieDetail(movieId)
                emit(Result.Success(response.asExternalModel()))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.flowOn(dispatcher)

    override fun getMovieVideos(movieId: Int): Flow<Result<MovieVideoItem>> =
        flow {
            try {
                val response =
                    tmdbService.getMovieVideos(movieId).results.first { it.type == "Trailer" }
                emit(Result.Success(response.asExternalModel()))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.flowOn(dispatcher)

    override fun getTopFiveMovieReviews(movieId: Int): Flow<Result<List<ReviewItem>>> =
        flow {
            try {
                val results = tmdbService.getMovieReviews(movieId).results
                val topFiveResults = results.take(5)

                val finalResults = if (results.size > 5)
                    topFiveResults + ReviewItemResponse.DEFAULT
                else
                    topFiveResults
                emit(Result.Success(finalResults.map(ReviewItemResponse::asExternalModel)))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.flowOn(dispatcher)

    override suspend fun getMovieReviews(page: Int, movieId: Int): ReviewResponse =
        tmdbService.getMovieReviews(page = page, movieId = movieId)

}