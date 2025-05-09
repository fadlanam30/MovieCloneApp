package tech.fadlan.moviecloneapp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tech.fadlan.moviecloneapp.domain.model.AuthorDetails
import tech.fadlan.moviecloneapp.domain.model.ReviewItem
import tech.fadlan.moviecloneapp.utils.formatToUSDateString

@JsonClass(generateAdapter = true)
data class ReviewResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<ReviewItemResponse>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class ReviewItemResponse(
    @Json(name = "author") val author: String,
    @Json(name = "author_details") val authorDetails: AuthorDetailsResponse,
    @Json(name = "content") val content: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "id") val id: String,
    @Json(name = "updated_at") val updatedAt: String,
    @Json(name = "url") val url: String
) {
    companion object {
        val DEFAULT = ReviewItemResponse(
            author = "",
            authorDetails = AuthorDetailsResponse(
                name = "",
                username = "",
                avatarPath = "",
                rating = 0.0
            ),
            content = "",
            createdAt = "",
            id = "",
            updatedAt = "",
            url = "",
        )
    }
}

@JsonClass(generateAdapter = true)
data class AuthorDetailsResponse(
    @Json(name = "name") val name: String?,
    @Json(name = "username") val username: String?,
    @Json(name = "avatar_path") val avatarPath: String?,
    @Json(name = "rating") val rating: Double?
)

fun ReviewItemResponse.asExternalModel() = ReviewItem(
    author = author,
    authorDetails = authorDetails.asExternalModel(),
    content = content,
    createdAtString = formatToUSDateString(createdAt),
    id = id,
    url = url
)

fun AuthorDetailsResponse.asExternalModel() = AuthorDetails(
    name = name ?: "",
    username = username ?: "",
    avatarPath = avatarPath ?: "",
    rating = rating ?: 0.0
)
