package com.fmollea.platzichallenge.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "previous_key")
    val previousKey: Int?,
    @ColumnInfo(name = "next_key")
    val nextKey: Int?
)