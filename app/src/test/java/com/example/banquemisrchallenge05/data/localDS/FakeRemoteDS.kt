package com.example.banquemisrchallenge05.data.localDS

import com.example.banquemisrchallenge05.data.remoteDS.RemoteDS
import com.example.banquemisrchallenge05.data.model.Genre
import com.example.banquemisrchallenge05.data.model.MovieDetailsResponse
import com.example.banquemisrchallenge05.data.model.ProductionCompany
import com.example.banquemisrchallenge05.data.model.ProductionCountry
import com.example.banquemisrchallenge05.data.model.SpokenLanguage
import com.example.banquemisrchallenge05.utils.test.movieDetailsResponse
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