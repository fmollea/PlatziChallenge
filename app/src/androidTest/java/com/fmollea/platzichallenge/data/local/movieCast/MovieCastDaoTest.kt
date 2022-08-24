package com.fmollea.platzichallenge.data.local.movieCast

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.fmollea.platzichallenge.data.local.AppDataBase
import com.fmollea.platzichallenge.data.model.movieCast.MovieCastEntity
import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieCastDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDataBase
    private lateinit var dao: MovieCastDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).build()
        dao = database.movieCastDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getMovieCastWithEmptyDB() = runTest {
        val allMovieCast = dao.getMovieCasts(movieId)
        assertThat(allMovieCast).isEmpty()
    }

    @Test
    fun getMovieCastWithDBNotEmpty() = runTest {
        dao.saveMovieCast(movieCast)
        val allMovieCast = dao.getMovieCasts(movieId)
        assertThat(allMovieCast).isNotEmpty()
    }

    @Test
    fun getMovieCastWithMovieIdDoesNotExist() = runTest {
        dao.saveMovieCast(movieCast)
        val allMovieCast = dao.getMovieCasts(movieIdDoesNotUsed)
        assertThat(allMovieCast).isEmpty()
        assertThat(allMovieCast).doesNotContain(movieCast)
    }

    @Test
    fun saveMovieCastAndCheckIt() = runTest {
        dao.saveMovieCast(movieCast)
        val allMovieCast = dao.getMovieCasts(movieId)

        assertThat(allMovieCast).contains(movieCast)
        assertThat(allMovieCast[0]).isEqualTo(movieCast)
        assertThat(allMovieCast[0].name).isEqualTo(movieCast.name)
        assertThat(allMovieCast[0].character).isEqualTo(movieCast.character)
    }

    @Test
    fun saveMovieCastWithDifferentMovieIdsAndGetMovieCastById() = runTest {
        dao.saveMovieCast(movieCast)
        dao.saveMovieCast(movieCast2)

        val allMovieCast = dao.getMovieCasts(movieId)
        val allMovieCastId2 = dao.getMovieCasts(movieId2)

        assertThat(allMovieCast).contains(movieCast)
        assertThat(allMovieCast).doesNotContain(movieCast2)

        assertThat(allMovieCastId2).doesNotContain(movieCast)
        assertThat(allMovieCastId2).contains(movieCast2)
    }

    companion object {
        const val movieIdDoesNotUsed = 0
        const val movieId = 1
        const val movieId2 = 2

        val movieCast = MovieCastEntity(
            id = 1,
            adult = false,
            gender = 0,
            knownForDepartment = "Acting",
            name = "Xochitl Gomez",
            originalName = "Xochitl Gomez",
            popularity = 4.5,
            profilePath = "/9UE2xtCDmyEXNHz6rPoJapHVoIZ.jpg",
            castsId = 1,
            character = "America Chavez",
            creditId = "",
            order = 2,
            movieId = movieId
        )
        val movieCast2 = MovieCastEntity(
            id = 2,
            adult = false,
            gender = 0,
            knownForDepartment = "Acting",
            name = "Benedict Cumberbatch",
            originalName = "Benedict Cumberbatch",
            popularity = 4.5,
            profilePath = "/fBEucxECxGLKVHBznO0qHtCGiMO.jpg",
            castsId = 1,
            character = "Dr. Stephen Strange",
            creditId = "",
            order = 2,
            movieId = movieId2
        )
    }
}