package com.fmollea.platzichallenge.di

import android.content.Context
import androidx.room.Room
import com.fmollea.platzichallenge.BuildConfig
import com.fmollea.platzichallenge.data.local.AppDataBase
import com.fmollea.platzichallenge.data.remote.movie.MovieService
import com.fmollea.platzichallenge.data.remote.movieCast.MovieCastService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "app_database"
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDataBase) = db.movieDao()

    @Singleton
    @Provides
    fun provideMovieKeyDao(db: AppDataBase) = db.movieKeyDao()

    @Singleton
    @Provides
    fun provideMovieCastDao(db: AppDataBase) = db.movieCastDao()


    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    @Singleton
    @Provides
    fun provideMovieCastService(retrofit: Retrofit) = retrofit.create(MovieCastService::class.java)

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)
}