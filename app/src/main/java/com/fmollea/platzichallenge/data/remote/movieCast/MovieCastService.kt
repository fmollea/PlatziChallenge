package com.fmollea.platzichallenge.data.remote.movieCast

import com.fmollea.platzichallenge.data.model.movieCast.MovieCastList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieCastService {
    @GET("movie/{id_movie}/casts")
    suspend fun getMovieCasts(@Path("id_movie") idMovie: Int, @Query("api_key") apiKey: String): MovieCastList
}