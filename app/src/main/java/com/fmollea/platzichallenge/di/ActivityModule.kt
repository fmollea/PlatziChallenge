package com.fmollea.platzichallenge.di

import androidx.paging.ExperimentalPagingApi
import com.fmollea.platzichallenge.repository.movie.MovieRepository
import com.fmollea.platzichallenge.repository.movie.MovieRepositoryImpl
import com.fmollea.platzichallenge.repository.movieCast.MovieCastRepository
import com.fmollea.platzichallenge.repository.movieCast.MovieCastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@OptIn(ExperimentalPagingApi::class)
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindMovieRepositoryImpl(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindMovieCastRepositoryImpl(movieCastRepositoryImpl: MovieCastRepositoryImpl): MovieCastRepository
}