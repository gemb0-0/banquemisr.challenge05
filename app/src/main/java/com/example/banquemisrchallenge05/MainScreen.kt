package com.example.banquemisrchallenge05

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05.ui.theme.Banquemisrchallenge05Theme
import com.example.banquemisrchallenge05.ui.theme.red

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(device = Devices.PIXEL_2_XL)
@Composable

fun MainScreen() {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomBar(navController) }
        ) {
            NavigationHost(navController = navController)
        }
}

@Composable
fun BottomBar(NavController: NavHostController) {
    val items = listOf(
        BottomNavItem.popular,
        BottomNavItem.nowPlaying,
        BottomNavItem.upComing
    )
    val navBackStackEntry by NavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Box(      modifier = Modifier
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


/*
@Composable
fun RowScope.addBottomNavigationItem(
    item: BottomNavItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = { Text(text = item.label) },
        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}*/
