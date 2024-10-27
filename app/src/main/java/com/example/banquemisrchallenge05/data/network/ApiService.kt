package com.example.banquemisrchallenge05.data.network

import com.example.banquemisrchallenge05.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("popular?")
    suspend fun getPopularMovies(
    @Query("page=") page: Int
    ): Response<MovieResponse>

    @GET("upcoming?")
    suspend fun getUpcomingMovies(
    @Query("page=") page: Int
    ): Response<MovieResponse>

    @GET("now_playing?")
    suspend fun getNowPlayingMovies(
    @Query("page=") page: Int
    ): Response<MovieResponse>

}