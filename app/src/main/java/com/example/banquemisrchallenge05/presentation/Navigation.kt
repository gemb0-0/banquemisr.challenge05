package com.example.banquemisrchallenge05.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.banquemisrchallenge05.viewModel.MovieDetailsViewModel
import com.example.banquemisrchallenge05.viewModel.MoviesViewModel


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object popular : BottomNavItem("popular", Icons.Filled.BarChart, "Popular")
    object nowPlaying : BottomNavItem("nowPlaying", Icons.Filled.Slideshow, "Now Playing")
    object upComing : BottomNavItem("upComing", Icons.Filled.Upcoming, "Up Coming")
}


@Composable
fun NavigationHost(
    navController: NavHostController,
    movieViewModel: MoviesViewModel,
    movieDeatilsViewModel: MovieDetailsViewModel,

    ){


    NavHost(navController = navController, startDestination = BottomNavItem.nowPlaying.route){
    composable("nowPlaying"){ NowPlaying(navController,movieViewModel) }
    composable("popular"){ Popular(navController,movieViewModel) }
    composable("upComing"){ UpComing(navController,movieViewModel) }
   composable("movieDetails/{id}") {
        val id = it.arguments?.getString("id")
        MovieDetailsScreen(id!!, navController,movieDeatilsViewModel)
   }
    }
}