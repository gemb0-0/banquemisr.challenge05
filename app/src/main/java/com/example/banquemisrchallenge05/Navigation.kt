package com.example.banquemisrchallenge05

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object popular : BottomNavItem("popular", Icons.Filled.Home, "Popular")
    object nowPlaying : BottomNavItem("nowPlaying", Icons.Filled.Search, "Now Playing")
    object upComing : BottomNavItem("upComing", Icons.Filled.Person, "Up Coming")
}


@Composable
fun NavigationHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = BottomNavItem.nowPlaying.route){
    composable("nowPlaying"){ NowPlaying() }
    composable("popular"){ Popular() }
    composable("upComing"){ UpComing() }
    }
}