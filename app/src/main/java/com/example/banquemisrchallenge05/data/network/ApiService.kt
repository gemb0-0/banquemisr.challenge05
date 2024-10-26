package com.example.banquemisrchallenge05.data.network

import com.example.banquemisrchallenge05.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("popular?page=1")
    suspend fun getPopularMovies(): Response<MovieResponse>
}