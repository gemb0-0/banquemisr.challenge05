package com.example.banquemisrchallenge05.data.repository

import com.example.banquemisrchallenge05.data.model.Genre
import com.example.banquemisrchallenge05.data.model.MovieDetailsResponse
import com.example.banquemisrchallenge05.data.model.ProductionCompany
import com.example.banquemisrchallenge05.data.model.ProductionCountry
import com.example.banquemisrchallenge05.data.model.SpokenLanguage
import com.example.banquemisrchallenge05.utils.test.movieDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepositoryImpl :Repository {
    override suspend fun getMovieDetails(id: String): Flow<MovieDetailsResponse>  = flow {
        val movie = movieDetailsResponse.find {
            it.id == id.toInt()
        }?: throw Exception("Movie not found")
        emit(movie)
    }
}