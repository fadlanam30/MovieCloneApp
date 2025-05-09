package tech.fadlan.moviecloneapp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.fadlan.moviecloneapp.domain.model.MovieVideoItem

@JsonClass(generateAdapter = true)
data class MovieVideosResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "results") val results: List<MovieVideoItemResponse>
)

@JsonClass(generateAdapter = true)
data class MovieVideoItemResponse(
    @Json(name = "name") val name: String,
    @Json(name = "key") val key: String,
    @Json(name = "site") val site: String,
    @Json(name = "size") val size: Int,
    @Json(name = "type") val type: String,
    @Json(name = "official") val official: Boolean,
    @Json(name = "published_at") val publishedAt: String,
    @Json(name = "id") val id: String
)

fun MovieVideoItemResponse.asExternalModel() = MovieVideoItem(
    id = id,
    name = name,
    key = key,
    site = site,
    size = size,
    type = type,
    official = official,
    publishedAt = publishedAt
)