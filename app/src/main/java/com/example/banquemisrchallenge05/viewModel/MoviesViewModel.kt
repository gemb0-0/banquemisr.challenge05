package com.example.banquemisrchallenge05.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.flatMap
import com.example.banquemisrchallenge05.data.repository.Repository
import com.example.banquemisrchallenge05.data.repository.RepositoryImpl
import com.example.banquemisrchallenge05.data.model.MovieDetailsResponse
import com.example.banquemisrchallenge05.data.model.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class MoviesViewModel @Inject constructor(
    @Named("Popular") popular_pager: Pager<Int, MovieResponse>,
    @Named("Upcoming") upcoming_pager: Pager<Int, MovieResponse>,
    @Named("NowPlaying") nowPlaying_pager: Pager<Int, MovieResponse>,
) : ViewModel() {

    val popularPager = popular_pager.flow.map { pagingData ->
        pagingData.flatMap { movieResponse ->
            movieResponse.results
        }
    }.cachedIn(viewModelScope)


    val upcomingPager = upcoming_pager.flow.map { pagingData ->
        pagingData.flatMap { movieResponse ->
            movieResponse.results
        }
    }.cachedIn(viewModelScope)


    val nowPlayingPager = nowPlaying_pager.flow.map { pagingData ->
        pagingData.flatMap { movieResponse ->
            movieResponse.results
        }
    }.cachedIn(viewModelScope)


}