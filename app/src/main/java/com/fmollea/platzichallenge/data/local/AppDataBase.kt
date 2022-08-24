package com.fmollea.platzichallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fmollea.platzichallenge.data.local.movie.MovieDao
import com.fmollea.platzichallenge.data.local.movie.MovieKeyDao
import com.fmollea.platzichallenge.data.local.movieCast.MovieCastDao
import com.fmollea.platzichallenge.data.model.movie.MovieEntity
import com.fmollea.platzichallenge.data.model.movie.MovieKeyEntity
import com.fmollea.platzichallenge.data.model.movieCast.MovieCastEntity

@Database(entities = [MovieEntity::class, MovieKeyEntity::class, MovieCastEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun movieKeyDao(): MovieKeyDao

    abstract fun movieCastDao(): MovieCastDao
}