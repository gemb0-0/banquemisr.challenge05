package com.example.banquemisrchallenge05.data.repository

import com.example.banquemisrchallenge05.model.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getMovieDetails(id: String) : Flow<MovieDetailsResponse>
}