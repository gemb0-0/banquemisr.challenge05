package com.example.banquemisrchallenge05.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05.ui.theme.red
import com.example.banquemisrchallenge05.utils.NetworkObserver
import com.example.banquemisrchallenge05.viewModel.MovieDetailsViewModel
import com.example.banquemisrchallenge05.viewModel.MoviesViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview(device = Devices.PIXEL_2_XL)
@Composable

fun MainScreen(
    networkObserver: NetworkObserver,
    movieViewModel: MoviesViewModel,
    movieDeatilsViewModel: MovieDetailsViewModel,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var showBottomBar by rememberSaveable { mutableStateOf(true) }

    val snackbarHostState = remember { SnackbarHostState() }


    showBottomBar = when (currentRoute) {
        "movieDetails/{id}" -> false
        else -> true
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),

        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController, currentRoute)
            }
        }
    ) {
        NavigationHost(navController = navController, movieViewModel = movieViewModel, movieDeatilsViewModel = movieDeatilsViewModel)

        LaunchedEffect(networkObserver.isConnected.value) {
            if (!networkObserver.isConnected.value)
            snackbarHostState.showSnackbar("Network connection lost")
        }

    }
}

@Composable
fun BottomBar(NavController: NavHostController, currentRoute: String?) {
    val items = listOf(
        BottomNavItem.popular,
        BottomNavItem.nowPlaying,
        BottomNavItem.upComing
    )
    Box(
        modifier = Modifier
            .padding(bottom = 25.dp, start = 50.dp, end = 50.dp)
            .clip(shape = RoundedCornerShape(50.dp)),
    ) {
        NavigationBar(
            modifier = Modifier.height(60.dp)

        ) {
            items.forEach() {
                NavigationBarItem(
                    modifier = Modifier.padding(top = 15.dp),
                    //label = { Text(text = it.label, ) },
                    icon = { Icon(imageVector = it.icon, contentDescription = it.label) },
                    selected = currentRoute == it.route,
                    //alwaysShowLabel = false,
                    colors = androidx.compose.material3.NavigationBarItemDefaults
                        .colors(
                            selectedIconColor = red,
                            indicatorColor = Color.Transparent
                        ),
                    onClick = {
                        NavController.navigate(it.route) {
                            popUpTo(NavController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

}