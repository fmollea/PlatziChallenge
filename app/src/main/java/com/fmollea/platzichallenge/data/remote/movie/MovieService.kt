package com.fmollea.platzichallenge.data.remote.movie

import com.fmollea.platzichallenge.data.model.movie.MovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): MovieList
}