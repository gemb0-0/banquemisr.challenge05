package com.example.banquemisrchallenge05.data.repository


import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.banquemisrchallenge05.data.localDS.MovieDataBase
import com.example.banquemisrchallenge05.data.network.ApiService
import com.example.banquemisrchallenge05.model.MovieResponse

@OptIn(ExperimentalPagingApi::class)
class UpComingMovieMediator(
    private val movieDB: MovieDataBase,
    private val movieAPI: ApiService

) : RemoteMediator<Int, MovieResponse>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieResponse>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.page + 1
                }
            }

            val response = movieAPI.getUpcomingMovies(loadKey)
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("MovieMediator", "Response: ${it.results}")
                    val movieResponse = MovieResponse(page = it.page, results = it.results,
                        totalPages = it.totalPages, totalResults = it.totalResults)
                    movieDB.movieDAO.insertMovie(movieResponse) //make sure to change this to insertUpComingMovie
                    MediatorResult.Success(endOfPaginationReached = it.page == it.totalPages)
                } ?: MediatorResult.Error(Exception("Response body is null"))
            } else {
                MediatorResult.Error(Exception("Response is not successful"))
            }
        } catch (e: Exception) {
            Log.i("MovieMediator", "Error: ${e.message}")
            MediatorResult.Error(e)
        }
    }

}