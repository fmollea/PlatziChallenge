package com.fmollea.platzichallenge.data.model.movieCast

import com.google.gson.annotations.SerializedName

data class MovieCastList(
    @SerializedName("cast")
    val movieCastList: List<MovieCast> = listOf()
)