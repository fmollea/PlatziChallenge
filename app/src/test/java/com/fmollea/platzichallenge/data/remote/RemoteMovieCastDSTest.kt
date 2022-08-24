package com.fmollea.platzichallenge.data.remote

import com.fmollea.platzichallenge.BuildConfig
import com.fmollea.platzichallenge.data.remote.movieCast.MovieCastService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class RemoteMovieCastDSTest {

    private val mockWebServer = MockWebServer()
    private val api: MovieCastService by lazy {
        generateRetrofit(mockWebServer).create(MovieCastService::class.java) }

    @Before
    fun setup() {
        mockWebServer.start(8080)
    }
    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get movie cast and is success`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(movieCastJson)
                .setResponseCode(200))

        val movieCast = api.getMovieCasts(1, BuildConfig.API_KEY)
        assertThat(movieCast.movieCastList).isNotEmpty()
    }

    @Test
    fun `get movie cast and failed`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(notFoundJson)
                .setResponseCode(200))

        val movieCast = api.getMovieCasts(1, BuildConfig.API_KEY)
        assertThat(movieCast.movieCastList).isEmpty()
    }

    @Test
    fun `get movie cast and throw exception with 404`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404))
        try {
            val movieCast = api.getMovieCasts(1, BuildConfig.API_KEY)
            assert(false)
        } catch (e: Exception) {
            assert(true)
        }
    }

    @Test
    fun `get movie cast and throw exception with 500`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500))
        try {
            val movieCast = api.getMovieCasts(1, BuildConfig.API_KEY)
            assert(false)
        } catch (e: Exception) {
            assert(true)
        }
    }

    companion object {
        const val movieCastJson =
            """{ 
            "id": 453395,
            "cast": [
            {
                "adult": false,
                "gender": 2,
                "id": 71580,
                "known_for_department": "Acting",
                "name": "Benedict Cumberbatch",
                "original_name": "Benedict Cumberbatch",
                "popularity": 77.926,
                "profile_path": "/fBEucxECxGLKVHBznO0qHtCGiMO.jpg",
                "cast_id": 2,
                "character": "Dr. Stephen Strange / Sinister Strange / Defender Strange",
                "credit_id": "58fa84bbc3a36879f40021db",
                "order": 0
            },
            {
                "adult": false,
                "gender": 1,
                "id": 550843,
                "known_for_department": "Acting",
                "name": "Elizabeth Olsen",
                "original_name": "Elizabeth Olsen",
                "popularity": 75.208,
                "profile_path": "/mbMsmQE5CyMVTIGMGCw2XpcPCOc.jpg",
                "cast_id": 10,
                "character": "Wanda Maximoff / The Scarlet Witch",
                "credit_id": "5d33bbc26a300b2f7ea6c7f4",
                "order": 1
            }
        ]}"""

        const val notFoundJson =
            """{
          "status_code": 7,
          "status_message": "Invalid API key: You must be granted a valid key.",
          "success": false
        }"""

        fun generateRetrofit(mockWebServer: MockWebServer): Retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Dummy url for testing
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}