package tech.fadlan.moviecloneapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tech.fadlan.moviecloneapp.data.response.DiscoverMovieResponse
import tech.fadlan.moviecloneapp.data.response.GenreResponse
import tech.fadlan.moviecloneapp.data.response.MovieDetailResponse
import tech.fadlan.moviecloneapp.data.response.MovieVideosResponse
import tech.fadlan.moviecloneapp.data.response.ReviewResponse

interface TMDBService {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int = 1,
        @Query("title") title: String,
        @Query("with_genres") genresIds: String,
    ): DiscoverMovieResponse

    @GET("genre/movie/list")
    suspend fun getGenresMovies(): GenreResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): MovieDetailResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
    ): MovieVideosResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1,
    ): ReviewResponse
}