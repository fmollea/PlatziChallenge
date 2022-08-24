package com.fmollea.platzichallenge.di

import androidx.paging.ExperimentalPagingApi
import com.fmollea.platzichallenge.data.remote.RemoteMovieCastDS
import com.fmollea.platzichallenge.data.local.LocalMovieCastDataSource
import com.fmollea.platzichallenge.data.local.movie.MovieDao
import com.fmollea.platzichallenge.data.remoteMediator.MovieRemoteMediator
import com.fmollea.platzichallenge.repository.movie.MovieRepositoryImpl
import com.fmollea.platzichallenge.repository.movieCast.MovieCastRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@OptIn(ExperimentalPagingApi::class)
@InstallIn(ViewModelComponent::class)
internal object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideMovieRepo(movieDao: MovieDao, movieRemoteMediator: MovieRemoteMediator) =
        MovieRepositoryImpl(movieDao, movieRemoteMediator)

    @Provides
    @ViewModelScoped
    fun provideMovieCastRepo(remoteDS: RemoteMovieCastDS, localDataSource: LocalMovieCastDataSource) =
        MovieCastRepositoryImpl(remoteDS, localDataSource)
}