package com.example.banquemisrchallenge05.data.remoteDS

import com.example.banquemisrchallenge05.data.model.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDS {
     fun getMovieDetails(id: String) : Flow<MovieDetailsResponse>
}