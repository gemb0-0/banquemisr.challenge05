package com.example.banquemisrchallenge05.data.localDS

import com.example.banquemisrchallenge05.data.remoteDS.RemoteDS
import com.example.banquemisrchallenge05.model.Genre
import com.example.banquemisrchallenge05.model.MovieDetailsResponse
import com.example.banquemisrchallenge05.model.ProductionCompany
import com.example.banquemisrchallenge05.model.ProductionCountry
import com.example.banquemisrchallenge05.model.SpokenLanguage
import com.example.banquemisrchallenge05.test.movieDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteDSImpl : RemoteDS {
    var isNetworkError = false
    var isListEmpty = false
    override fun getMovieDetails(id: String): Flow<MovieDetailsResponse> = flow {
        if (isNetworkError) {
            throw Exception("Network error")
        }
        if (isListEmpty) {
            throw Exception("The list is empty")
        }
        val movie = movieDetailsResponse.find {
            it.id == id.toInt()
        }?: throw Exception("Movie not found")
        emit(movie)
    }

}