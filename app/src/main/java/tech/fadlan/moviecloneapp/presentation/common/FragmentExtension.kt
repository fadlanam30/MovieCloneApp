package tech.fadlan.moviecloneapp.presentation.common

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

inline fun <T> Fragment.collectUiState(
    flow: StateFlow<UiState<T>>,
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: (T) -> Unit = {},
    crossinline onError: (Throwable) -> Unit = {}
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> onLoading()
                    is UiState.Success -> onSuccess(state.data)
                    is UiState.Error -> state.exception?.let { onError(it) }
                }
            }
        }
    }
}

inline fun <T> Fragment.collectFlow(
    flow: StateFlow<T>,
    crossinline onCollectData: (T) -> Unit = {},
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { data ->
                onCollectData(data)
            }
        }
    }
}

fun Fragment.registerOnBackPressHandler() {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val navController = findNavController()
                if (!navController.navigateUp()) {
                    requireActivity().finish()
                }
            }
        }
    )
}