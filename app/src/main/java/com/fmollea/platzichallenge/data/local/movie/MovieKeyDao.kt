package com.fmollea.platzichallenge.data.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fmollea.platzichallenge.data.model.movie.MovieKeyEntity

@Dao
interface MovieKeyDao {

    @Query("SELECT * FROM MovieKeyEntity WHERE movie_id = :movieId")
    suspend fun getMovieKey(movieId: Int): MovieKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieKey(movieKeyEntity: List<MovieKeyEntity>)

    @Query("DELETE FROM MovieKeyEntity")
    suspend fun clearMovieKeys()
}