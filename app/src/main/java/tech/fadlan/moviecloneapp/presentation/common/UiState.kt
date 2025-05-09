package tech.fadlan.moviecloneapp.presentation.common

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error<T>(val exception: Throwable?) : UiState<T>
    data object Loading : UiState<Nothing>
}