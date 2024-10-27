package com.example.banquemisrchallenge05.data.localDS

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.banquemisrchallenge05.model.MovieResponse
import com.example.banquemisrchallenge05.utils.Converters


@Database(entities = [MovieResponse::class], version = 1)
@TypeConverters(Converters::class)

abstract class MovieDataBase : RoomDatabase() {
   abstract val movieDAO: MovieDAO
}
