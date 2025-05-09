package tech.fadlan.moviecloneapp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.fadlan.moviecloneapp.domain.model.MovieDetail
import tech.fadlan.moviecloneapp.utils.formatLocalDate
import tech.fadlan.moviecloneapp.utils.formatMinutesToHourMinute
import tech.fadlan.moviecloneapp.utils.formatToThousands

@JsonClass(generateAdapter = true)
data class MovieDetailResponse(
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "genres") val genres: List<GenreItemResponse>,
    @Json(name = "homepage") val homepage: String?,
    @Json(name = "id") val id: Int,
    @Json(name = "overview") val overview: String?,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "title") val title: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "revenue") val revenue: Long,
    @Json(name = "runtime") val runtime: Int,
    @Json(name = "status") val status: String,
    @Json(name = "video") val video: Boolean,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int
)

fun MovieDetailResponse.asExternalModel() = MovieDetail(
    id = id,
    title = title,
    adult = adult,
    overview = overview ?: "",
    posterPath = posterPath ?: "",
    backdropPath = backdropPath ?: "",
    releaseLocalDate = formatLocalDate(releaseDate),
    voteAverage = voteAverage,
    voteCountString = formatToThousands(voteCount),
    genres = genres.map(GenreItemResponse::asExternalModel),
    video = video,
    popularity = popularity,
    revenue = revenue,
    homepage = homepage ?: "",
    durationString = formatMinutesToHourMinute(runtime),
    status = status,
)