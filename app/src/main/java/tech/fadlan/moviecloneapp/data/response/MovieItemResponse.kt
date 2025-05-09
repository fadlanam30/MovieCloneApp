package tech.fadlan.moviecloneapp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.fadlan.moviecloneapp.domain.model.MovieItem


@JsonClass(generateAdapter = true)
data class DiscoverMovieResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<MovieItemResponse>,
    @Json(name = "total_pages")
    val totalPages: Int,
)

@JsonClass(generateAdapter = true)
data class MovieItemResponse(
    @Json(name = "adult")
    val adult: Boolean,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "genre_ids")
    val genreIds: List<Int>,

    @Json(name = "id")
    val id: Int,

    @Json(name = "original_language")
    val originalLanguage: String,

    @Json(name = "original_title")
    val originalTitle: String,

    @Json(name = "overview")
    val overview: String,

    @Json(name = "popularity")
    val popularity: Double,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "release_date")
    val releaseDate: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "video")
    val video: Boolean,

    @Json(name = "vote_average")
    val voteAverage: Double,

    @Json(name = "vote_count")
    val voteCount: Int
){
    companion object {
        val DEFAULT = MovieItemResponse(
            id = 0,
            title = "",
            overview = "",
            posterPath = null,
            backdropPath = null,
            releaseDate = "",
            voteAverage = 0.0,
            voteCount = 0,
            genreIds = emptyList(),
            originalLanguage = "",
            originalTitle = "",
            video = false,
            adult = false,
            popularity = 0.0,
        )
    }
}

fun MovieItemResponse.asExternalModel() = MovieItem(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath ?: "",
    backdropPath = backdropPath?: "",
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genreIds = genreIds,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    video = video,
    adult = adult,
    popularity = popularity,
)