package com.fmollea.platzichallenge.ui.movie.adapters

import com.fmollea.platzichallenge.data.model.movie.Movie

interface OnMovieClickListener {
    fun onMovieClick(movie: Movie)
}