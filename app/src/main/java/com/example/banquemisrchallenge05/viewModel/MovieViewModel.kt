package com.example.banquemisrchallenge05.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banquemisrchallenge05.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(private val repo :Repository) : ViewModel() {
    fun getPopularMovies() {
        Log.i("MovieViewModel", "getPopularMovies: ")
       viewModelScope.launch {
           repo.getPopularMovies()
       }
    }

}