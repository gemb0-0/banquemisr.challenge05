package com.example.banquemisrchallenge05.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banquemisrchallenge05.data.network.ApiState
import com.example.banquemisrchallenge05.data.repository.Repository
import com.example.banquemisrchallenge05.model.MovieDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

    private val _movieDetails = MutableStateFlow<ApiState>(ApiState.Loading)
    val movieDetails: StateFlow<ApiState> = _movieDetails
    fun getMovieDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getMovieDetails(id).catch {
                _movieDetails.value = ApiState.Failure(it.message?:"Unknown Error")
            }.collect {
                _movieDetails.value = ApiState.Success(it)
            }

        }
    }

}