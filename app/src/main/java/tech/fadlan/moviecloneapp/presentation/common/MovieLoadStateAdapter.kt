package tech.fadlan.moviecloneapp.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.fadlan.moviecloneapp.databinding.MovieLoadItemLayoutBinding

class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            MovieLoadItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(
        private val binding: MovieLoadItemLayoutBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {

            binding.apply {
                progressBar.visibility =
                    if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
                retryBtn.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
                retryBtn.setOnClickListener {
                    retry()
                }
            }
        }
    }
}