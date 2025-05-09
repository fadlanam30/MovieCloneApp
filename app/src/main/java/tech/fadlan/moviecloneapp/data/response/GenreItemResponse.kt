package tech.fadlan.moviecloneapp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.fadlan.moviecloneapp.domain.model.GenreItem

@JsonClass(generateAdapter = true)
data class GenreResponse(
    @Json(name = "genres")
    val genres: List<GenreItemResponse>,
)

@JsonClass(generateAdapter = true)
data class GenreItemResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
)

fun GenreItemResponse.asExternalModel() = GenreItem(id, name)
