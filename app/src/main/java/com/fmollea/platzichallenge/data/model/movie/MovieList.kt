package com.fmollea.platzichallenge.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("results")
    val movieList: List<Movie> = listOf()
)
