package com.example.banquemisrchallenge05.viewModel

import com.example.banquemisrchallenge05.data.network.ApiState
import com.example.banquemisrchallenge05.data.repository.FakeRepositoryImpl
import com.example.banquemisrchallenge05.utils.test.movieDetailsResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class MovieDetailsViewModelTest() {
    val expectedMovie = movieDetailsResponse[0]

    lateinit var viewModel: MovieDetailsViewModel
    lateinit var repository: FakeRepositoryImpl
    @Before
    fun setup() {
        repository = FakeRepositoryImpl()
        viewModel = MovieDetailsViewModel(repository)
    }




    @Test
    fun test_getMovieDetails_vaild_id()= runTest{
        viewModel.getMovieDetails(123456.toString())
        assertEquals(viewModel.movieDetails.value, ApiState.Loading)
        val result = viewModel.movieDetails.first{it is ApiState.Success}
        assertTrue(result is ApiState.Success)
        val movie = (result as ApiState.Success).data
        assertEquals(movie, expectedMovie)
    }

    @Test
    fun test_getMovieDetails_invaild_id()= runTest{
        viewModel.getMovieDetails("999911223")
        assertEquals(viewModel.movieDetails.value, ApiState.Loading)
        val result = viewModel.movieDetails.first{it is ApiState.Failure}
        assertTrue(result is ApiState.Failure)
        val movie = (result as ApiState.Failure).message
        assertEquals(movie, "Movie not found")
    }


}
