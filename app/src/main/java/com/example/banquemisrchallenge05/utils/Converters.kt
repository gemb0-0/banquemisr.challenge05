package com.example.banquemisrchallenge05.utils

import androidx.room.TypeConverter
import com.example.banquemisrchallenge05.data.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<Movie> {
        val listType = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Movie>): String {
        return gson.toJson(list)
    }
}