package com.fmollea.platzichallenge.data.local.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fmollea.platzichallenge.data.model.movie.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity ORDER BY id ASC")
    fun getMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM MovieEntity ORDER BY id ASC")
    fun getMoviesForTest(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movie: List<MovieEntity>)

    @Query("DELETE FROM MovieEntity ")
    suspend fun clearMovies()
}