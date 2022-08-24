package com.fmollea.platzichallenge.repository.movieCast

import com.fmollea.platzichallenge.data.model.movieCast.MovieCastList

interface MovieCastRepository {
    suspend fun getMovieCasts(movieId: Int): MovieCastList
}