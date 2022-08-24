package com.fmollea.platzichallenge.ui.movieDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.fmollea.platzichallenge.BuildConfig
import com.fmollea.platzichallenge.R
import com.fmollea.platzichallenge.core.Resource
import com.fmollea.platzichallenge.databinding.FragmentMovieDetailBinding
import com.fmollea.platzichallenge.presentation.MovieViewModel
import com.fmollea.platzichallenge.ui.movieDetail.adapters.MovieCastAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailBinding.bind(view)
        observeCurrentMovie()
    }

    private fun observeCurrentMovie() {
        movieViewModel.currentMovie.observe(this.viewLifecycleOwner) {
            Glide.with(requireContext()).load("${BuildConfig.IMAGE_URL}${it.posterPath}")
                .centerCrop()
                .into(binding.imgMovie)

            Glide.with(requireContext()).load("${BuildConfig.IMAGE_URL}${it.backdropPath}")
                .centerCrop()
                .into(binding.imgBackground)

            binding.tvTitle.text = it.title
            binding.tvOverview.text = it.overview
            binding.tvLanguage.text = "Language: ${it.originalLanguage}"
            val score = (it.voteAverage * 10).toInt()
            binding.tvUserScore.text = "User score: $score%"
            binding.tvReleasedDate.text = "Released date: ${it.releaseDate}"

            observeViewModel(it.id)
        }


    }

    private fun observeViewModel(movieId: Int) {
        movieViewModel.fetchMovieCasts(movieId ?: -1).observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {
                    binding.rvMovieCast.visibility = View.GONE
                    binding.tvCastLabel.visibility = View.GONE
                }
                is Resource.Success -> {
                    val cast = result.data.movieCastList
                    if (cast.isNotEmpty()) {
                        binding.rvMovieCast.visibility = View.VISIBLE
                        binding.tvCastLabel.visibility = View.VISIBLE
                        binding.rvMovieCast.adapter = MovieCastAdapter(cast)
                    }
                }
                is Resource.Failure -> {
                    binding.rvMovieCast.visibility = View.GONE
                    binding.tvCastLabel.visibility = View.GONE
                }
            }
        }
    }
}