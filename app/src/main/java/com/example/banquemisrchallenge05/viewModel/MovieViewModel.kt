package com.example.banquemisrchallenge05.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.flatMap
import androidx.paging.map
import com.example.banquemisrchallenge05.model.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class MovieViewModel @Inject constructor(
    @Named("Popular") popular_pager: Pager<Int, MovieResponse>,
    @Named("Upcoming") upcoming_pager: Pager<Int, MovieResponse>,
    @Named("NowPlaying") nowPlaying_pager: Pager<Int, MovieResponse>
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