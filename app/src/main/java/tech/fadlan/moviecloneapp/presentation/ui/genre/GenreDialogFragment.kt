package tech.fadlan.moviecloneapp.presentation.ui.genre

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import tech.fadlan.moviecloneapp.R
import tech.fadlan.moviecloneapp.databinding.FragmentGenreDialogBinding
import tech.fadlan.moviecloneapp.domain.model.GenreItem
import tech.fadlan.moviecloneapp.presentation.common.collectUiState
import tech.fadlan.moviecloneapp.presentation.ui.home.HomeViewModel

@AndroidEntryPoint
class GenreDialogFragment(
    private val selectedGenreParameter: GenreItem,
    private val onClickGenre: (GenreItem) -> Unit
) : DialogFragment(R.layout.fragment_genre_dialog) {

    private val binding: FragmentGenreDialogBinding by viewBinding(FragmentGenreDialogBinding::bind)

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var genreAdapter: GenreAdapter

    override fun onStart() {
        super.onStart()
        setupDialogLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genreAdapter = GenreAdapter(
            selectedGenre = selectedGenreParameter,
            onGenreClick = { selectedGenre ->
                onClickGenre(selectedGenre)
                dismiss()
            }
        )

        binding.closeCategoryButton.setOnClickListener {
            dismiss()
        }

        binding.rvGenres.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = genreAdapter
        }
        collectUiState(
            flow = viewModel.genres,
            onSuccess = { genres ->
                binding.root.visibility = View.VISIBLE
                genreAdapter.submitList(genres)
            },
            onLoading = {
                binding.root.visibility = View.GONE
            },
            onError = {
                binding.root.visibility = View.GONE
                Log.e("GenreDialogFragment", "onError: $it")
            }
        )
    }

    private fun setupDialogLayout() {
        val width = resources.displayMetrics.widthPixels

        dialog?.window?.apply {
            setGravity(Gravity.CENTER)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(width, WindowManager.LayoutParams.MATCH_PARENT)
        }
    }
}