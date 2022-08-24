package com.fmollea.platzichallenge.data.model.movieCast

import com.google.gson.annotations.SerializedName

data class MovieCast(
    val id: Int = -1,
    val adult: Boolean = false,
    val gender: Int = -1,
    @SerializedName("known_for_department")
    val knownForDepartment: String = "",
    val name: String = "",
    @SerializedName("original_name")
    val originalName: String = "",
    val popularity: Double = -1.0,
    @SerializedName("profile_path")
    val profilePath: String? = "",
    @SerializedName("casts_id")
    val castsId: Int = -1,
    val character: String = "",
    @SerializedName("credit_id")
    val creditId: String = "",
    val order: Int = -1
) {
    fun toMovieCastEntity(idMovie: Int) = MovieCastEntity(
        this.id,
        this.adult,
        this.gender,
        this.knownForDepartment,
        this.name,
        this.originalName,
        this.popularity,
        this.profilePath?: "",
        this.castsId,
        this.character,
        this.creditId,
        this.order,
        idMovie
    )
}