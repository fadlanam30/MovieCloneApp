package tech.fadlan.moviecloneapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewItem(
    val author: String,
    val authorDetails: AuthorDetails,
    val content: String,
    val createdAtString: String,
    val id: String,
    val url: String
) : Parcelable

@Parcelize
data class AuthorDetails(
    val name: String,
    val username: String,
    val avatarPath: String,
    val rating: Double
) : Parcelable
