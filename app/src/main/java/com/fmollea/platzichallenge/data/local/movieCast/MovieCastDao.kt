package com.fmollea.platzichallenge.data.local.movieCast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fmollea.platzichallenge.data.model.movieCast.MovieCastEntity

@Dao
interface MovieCastDao {
    @Query("SELECT * FROM MovieCastEntity WHERE movie_id = :movieId")
    suspend fun getMovieCasts(movieId: Int): List<MovieCastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieCast(movieCast: MovieCastEntity)
}