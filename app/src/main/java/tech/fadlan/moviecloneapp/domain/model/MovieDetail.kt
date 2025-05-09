package tech.fadlan.moviecloneapp.domain.model

import java.time.LocalDate


data class MovieDetail(
    val backdropPath: String,
    val genres: List<GenreItem>,
    val homepage: String,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseLocalDate: LocalDate,
    val revenue: Long,
    val durationString: String,
    val status: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCountString: String,
    val title: String,
    val adult: Boolean
)