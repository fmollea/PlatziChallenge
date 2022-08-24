package com.fmollea.platzichallenge.data.local.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fmollea.platzichallenge.data.local.AppDataBase
import com.fmollea.platzichallenge.data.model.movie.MovieEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDataBase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        dao = database.movieDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getMovieWithEmptyDB() = runTest {
        val allMovies = dao.getMoviesForTest()
        assertThat(allMovies).isEmpty()
    }

    @Test
    fun saveMovies() = runTest {
        val list = listOf(movie, movie2)
        dao.saveMovies(list)

        val allMovies = dao.getMoviesForTest()

        assertThat(allMovies).isNotEmpty()
        assertThat(allMovies).contains(movie)
        assertThat(allMovies).contains(movie2)
    }

    @Test
    fun saveTheSameMoviesManyTimesAndDoNotDuplicateIt() = runTest {
        val list = listOf(movie, movie, movie)
        dao.saveMovies(list)

        val allMovies = dao.getMoviesForTest()

        assertThat(allMovies).isNotEmpty()
        assertThat(allMovies).contains(movie)
        assertThat(allMovies.size).isEqualTo(1)
    }

    @Test
    fun clearDB() = runTest {
        val list = listOf(movie, movie2)
        dao.saveMovies(list)
        dao.clearMovies()
        val allMovies = dao.getMoviesForTest()

        assertThat(allMovies).isEmpty()
        assertThat(allMovies).doesNotContain(movie)
        assertThat(allMovies).doesNotContain(movie2)
    }


    companion object {
        val movie = MovieEntity(
            id = 1,
            remoteId = 1,
            adult = false,
            backdropPath = "/wcKFYIiVDvRURrzglV9kGu7fpfY.jpg",
            originalLanguage = "en",
            overview = "very long text...",
            popularity = 6.5,
            posterPath = "/9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg",
            releaseDate = "2022-05-04",
            title = "Doctor Strange in the Multiverse of Madness",
            video = false,
            voteAverage = 7.6,
            voteCount = 3025
        )
        val movie2 = MovieEntity(
            id = 2,
            remoteId = 11,
            adult = false,
            backdropPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            originalLanguage = "en",
            overview = "very long text...",
            popularity = 9.5,
            posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            releaseDate = "2021-12-15",
            title = "Spider-Man: No Way Home",
            video = false,
            voteAverage = 8.1,
            voteCount = 13024
        )
    }
}