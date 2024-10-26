package com.example.banquemisrchallenge05.data.repository

interface Repository {
    suspend fun getPopularMovies()
}