package com.fmollea.platzichallenge.data.model.movieCast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieCastEntity(
    @PrimaryKey
    val id: Int = -1,
    @ColumnInfo(name = "adult")
    val adult: Boolean = false,
    @ColumnInfo(name = "gender")
    val gender: Int = -1,
    @ColumnInfo(name = "known_for_department")
    val knownForDepartment: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "original_name")
    val originalName: String = "",
    @ColumnInfo(name = "popularity")
    val popularity: Double = -1.0,
    @ColumnInfo(name = "profile_path")
    val profilePath: String = "",
    @ColumnInfo(name = "casts_id")
    val castsId: Int = -1,
    @ColumnInfo(name = "character")
    val character: String = "",
    @ColumnInfo(name = "credit_id")
    val creditId: String = "",
    @ColumnInfo(name = "order")
    val order: Int = -1,
    @ColumnInfo(name = "movie_id")
    val movieId: Int = -1
) {
    fun toMovieCast() = MovieCast(
        this.id,
        this.adult,
        this.gender,
        this.knownForDepartment,
        this.name,
        this.originalName,
        this.popularity,
        this.profilePath,
        this.castsId,
        this.character,
        this.creditId,
        this.order
    )
}