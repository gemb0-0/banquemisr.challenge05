package com.example.banquemisrchallenge05.data.remoteDS


import android.util.Log
import com.example.banquemisrchallenge05.data.network.ApiService
import com.example.banquemisrchallenge05.data.model.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDSImpl @Inject constructor( val api: ApiService) : RemoteDS {
    override fun getMovieDetails(id: String): Flow<MovieDetailsResponse> = flow {
       val response =  api.getMovieDetails(id)
        if (response.isSuccessful && response.body() != null){
            response.body()?.let {
                emit(it)
            }
        }
        else{
            throw Exception("Failed to fetch movie details")
        }

    }

}