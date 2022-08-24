package com.fmollea.platzichallenge.repository.movie

import androidx.paging.PagingData
import com.fmollea.platzichallenge.data.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
}