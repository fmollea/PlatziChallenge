package com.fmollea.platzichallenge.ui.movie

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.fmollea.platzichallenge.R
import com.fmollea.platzichallenge.data.model.movie.Movie
import com.fmollea.platzichallenge.databinding.FragmentMovieBinding
import com.fmollea.platzichallenge.presentation.MovieViewModel
import com.fmollea.platzichallenge.ui.movie.adapters.MovieAdapter
import com.fmollea.platzichallenge.ui.movie.adapters.OnMovieClickListener
import com.fmollea.platzichallenge.ui.movie.callback.MovieListOnBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie), OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieAdapter: MovieAdapter
    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieBinding.bind(view)
        movieAdapter = MovieAdapter(this@MovieFragment)
        binding.rvMovies.adapter = movieAdapter

        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            MovieListOnBackPressedCallback(slidingPaneLayout)
        )
        loadStates()
        observeViewModel()
    }

    private fun loadStates() {
        movieAdapter.addLoadStateListener { loadState->
            val isLoading = loadState.source.refresh is LoadState.Loading

            binding.pbLoading.isVisible = isLoading

            val errorState = loadState.source.refresh as? LoadState.Error

            binding.cdErrorOccurred.isVisible = errorState is LoadState.Error

            errorState?.let {
                binding.btTryAgain.setOnClickListener {
                    observeViewModel()

                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            movieViewModel.fetchMovies()
                .collect() {
                    movieAdapter.submitData(lifecycle,it)
                }
        }
    }

    override fun onMovieClick(movie: Movie) {
        movieViewModel.updateCurrentMovie(movie)
        binding.slidingPaneLayout.openPane()
    }
}