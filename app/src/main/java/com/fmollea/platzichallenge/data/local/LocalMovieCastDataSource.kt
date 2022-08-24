package com.fmollea.platzichallenge.data.local

import com.fmollea.platzichallenge.data.local.movieCast.MovieCastDao
import com.fmollea.platzichallenge.data.model.movieCast.MovieCastEntity
import com.fmollea.platzichallenge.data.model.movieCast.MovieCastList
import javax.inject.Inject

class LocalMovieCastDataSource @Inject constructor(private val movieCastDao: MovieCastDao) {

    suspend fun getMovieCasts(movieId: Int) = MovieCastList (
        movieCastDao.getMovieCasts(movieId).map { movieCastEntity -> movieCastEntity.toMovieCast() }
    )

    suspend fun saveMovieCast(movieCast: MovieCastEntity) {
        movieCastDao.saveMovieCast(movieCast)
    }
}