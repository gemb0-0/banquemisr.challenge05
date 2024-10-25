package com.example.banquemisrchallenge05

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object popular : BottomNavItem("popular", Icons.Filled.BarChart, "Popular")
    object nowPlaying : BottomNavItem("nowPlaying", Icons.Filled.Slideshow, "Now Playing")
    object upComing : BottomNavItem("upComing", Icons.Filled.Upcoming, "Up Coming")
}


@Composable
fun NavigationHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = BottomNavItem.nowPlaying.route){
    composable("nowPlaying"){ NowPlaying() }
    composable("popular"){ Popular() }
    composable("upComing"){ UpComing() }
    }
}