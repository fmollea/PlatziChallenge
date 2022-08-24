package com.fmollea.platzichallenge.data.model.movie

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int = -1,
    val adult: Boolean = false,
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    @SerializedName("original_language")
    val originalLanguage: String = "",
    val overview: String = "",
    val popularity: Double = -1.0,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Double = -1.0,
    @SerializedName("vote_count")
    val voteCount: Int = -1
) {
    fun toMovieEntity() = MovieEntity(
        remoteId = this.id.toLong(),
        adult = this.adult,
        backdropPath = this.backdropPath?: "",
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}