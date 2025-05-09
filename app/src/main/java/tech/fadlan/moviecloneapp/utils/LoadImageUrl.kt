package tech.fadlan.moviecloneapp.utils

import tech.fadlan.moviecloneapp.BuildConfig

fun loadImageUrl(url: String): String {
    return BuildConfig.TMDB_IMAGE_BASE_URL + url
}