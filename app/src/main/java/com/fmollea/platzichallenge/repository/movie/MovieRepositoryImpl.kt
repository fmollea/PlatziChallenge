package com.fmollea.platzichallenge.repository.movie

import androidx.paging.*
import com.fmollea.platzichallenge.data.local.movie.MovieDao
import com.fmollea.platzichallenge.data.remoteMediator.MovieRemoteMediator
import com.fmollea.platzichallenge.data.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieRemoteMediator: MovieRemoteMediator
) : MovieRepository {

    override fun getMovies(): Flow<PagingData<Movie>> {
        return  Pager(
            config = defaultPagingConfig(),
            remoteMediator = movieRemoteMediator,
            pagingSourceFactory = { movieDao.getMovies() }
        ).flow.map { data ->
            data.map { it.toMovie() }
        }
    }

    private fun defaultPagingConfig() = PagingConfig(
        pageSize = 20,
        enablePlaceholders = false,
        prefetchDistance = 20,
        maxSize = 60
    )
}