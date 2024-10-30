package com.example.banquemisrchallenge05.data.repoistory

import com.example.banquemisrchallenge05.data.model.MovieDetailsResponse
import com.example.banquemisrchallenge05.data.repository.Repository
import com.example.banquemisrchallenge05.utils.test.movieDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepositoryImpl : Repository {
    override suspend fun getMovieDetails(id: String): Flow<MovieDetailsResponse>  = flow {
        val movie = movieDetailsResponse.find {
            it.id == id.toInt()
        }?: throw Exception("Movie not found")
        emit(movie)
    }
}