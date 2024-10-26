package com.example.banquemisrchallenge05.data.repository

import android.util.Log
import com.example.banquemisrchallenge05.data.remoteDS.RemoteDS
import com.example.banquemisrchallenge05.data.remoteDS.RemoteDSImpl
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDS : RemoteDS) : Repository {


    override suspend fun getPopularMovies(){
        Log.i("MovieViewModel", "getPopularMovies: repo")
        remoteDS.getPopularMovies()
    }

}