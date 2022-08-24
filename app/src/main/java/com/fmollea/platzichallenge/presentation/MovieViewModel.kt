package com.fmollea.platzichallenge.presentation

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fmollea.platzichallenge.core.Resource
import com.fmollea.platzichallenge.data.model.movie.Movie
import com.fmollea.platzichallenge.repository.movie.MovieRepository
import com.fmollea.platzichallenge.repository.movieCast.MovieCastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MovieRepository,
    private val movieCastRepo: MovieCastRepository
): ViewModel() {

    private var _currentMovie: MutableLiveData<Movie> = MutableLiveData<Movie>()
    val currentMovie: LiveData<Movie>
        get() = _currentMovie

    fun updateCurrentMovie(movie: Movie) {
        _currentMovie.value = movie
    }

    fun fetchMovies(): Flow<PagingData<Movie>> {
        return movieRepo.getMovies().cachedIn(viewModelScope)
    }

    fun fetchMovieCasts(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(movieCastRepo.getMovieCasts(movieId)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}