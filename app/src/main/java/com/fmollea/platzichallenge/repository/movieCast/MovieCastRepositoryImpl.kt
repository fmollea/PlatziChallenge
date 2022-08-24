package com.fmollea.platzichallenge.repository.movieCast

import com.fmollea.platzichallenge.core.NetworkCheck
import com.fmollea.platzichallenge.data.remote.RemoteMovieCastDS
import com.fmollea.platzichallenge.data.local.LocalMovieCastDataSource
import com.fmollea.platzichallenge.data.model.movieCast.MovieCastList
import javax.inject.Inject

class MovieCastRepositoryImpl @Inject constructor(
    private val remoteDS: RemoteMovieCastDS,
    private val localDataSource: LocalMovieCastDataSource
): MovieCastRepository {

    override suspend fun getMovieCasts(movieId: Int): MovieCastList {
        if (NetworkCheck.isNetworkAvailable()) {
            remoteDS.getMovieCasts(movieId).movieCastList.forEach { movieCast ->
                localDataSource.saveMovieCast(movieCast.toMovieCastEntity(movieId))
            }
        }
        return localDataSource.getMovieCasts(movieId)
    }
}