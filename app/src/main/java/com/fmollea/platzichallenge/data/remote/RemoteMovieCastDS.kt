package com.fmollea.platzichallenge.data.remote

import com.fmollea.platzichallenge.BuildConfig
import com.fmollea.platzichallenge.data.remote.movieCast.MovieCastService
import javax.inject.Inject

class RemoteMovieCastDS @Inject constructor(private val movieCastService: MovieCastService) {
    suspend fun getMovieCasts(idMovie: Int) = movieCastService.getMovieCasts(idMovie, BuildConfig.API_KEY)
}