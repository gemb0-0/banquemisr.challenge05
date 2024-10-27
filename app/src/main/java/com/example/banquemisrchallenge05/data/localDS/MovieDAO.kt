package com.example.banquemisrchallenge05.data.localDS

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.banquemisrchallenge05.model.MovieResponse

@Dao
interface MovieDAO {

    @Upsert
    suspend fun insertMovie(movie: MovieResponse)

    @Query("SELECT * FROM MovieResponse")
    fun pagingSource(): PagingSource<Int, MovieResponse>

}