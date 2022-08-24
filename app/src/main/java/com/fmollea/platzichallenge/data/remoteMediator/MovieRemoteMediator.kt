package com.fmollea.platzichallenge.data.remoteMediator

import androidx.paging.*
import androidx.room.withTransaction
import com.fmollea.platzichallenge.BuildConfig
import com.fmollea.platzichallenge.data.local.AppDataBase
import com.fmollea.platzichallenge.data.local.movie.MovieDao
import com.fmollea.platzichallenge.data.local.movie.MovieKeyDao
import com.fmollea.platzichallenge.data.model.movie.MovieEntity
import com.fmollea.platzichallenge.data.model.movie.MovieKeyEntity
import com.fmollea.platzichallenge.data.remote.movie.MovieService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val movieService: MovieService,
    private val appDataBase: AppDataBase,
    private val movieDao: MovieDao,
    private val movieKeyDao: MovieKeyDao
) : RemoteMediator<Int, MovieEntity>() {

    private val STARTING_PAGE = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.previousKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = movieService.getMovies(BuildConfig.API_KEY, page)

            val movies = apiResponse.movieList
            val endOfPaginationReached = movies.isEmpty()
            appDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieKeyDao.clearMovieKeys()
                    movieDao.clearMovies()
                }
                val prevKey = if (page == STARTING_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    MovieKeyEntity(it.id, prevKey, nextKey)
                }
                movieKeyDao.saveMovieKey(keys)
                movieDao.saveMovies(movies.map { movie -> movie.toMovieEntity() }.toList())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): MovieKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieKeyDao.getMovieKey(movie.remoteId.toInt())
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): MovieKeyEntity? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieKeyDao.getMovieKey(movie.remoteId.toInt())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): MovieKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.remoteId?.let { movieId ->
                movieKeyDao.getMovieKey(movieId.toInt())
            }
        }
    }
}