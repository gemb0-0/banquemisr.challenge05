package com.example.banquemisrchallenge05.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.banquemisrchallenge05.data.remoteDS.RemoteDS
import com.example.banquemisrchallenge05.model.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDS: RemoteDS) : Repository {

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getMovieDetails(id: String): Flow<MovieDetailsResponse> {
       return remoteDS.getMovieDetails(id)
    }


}