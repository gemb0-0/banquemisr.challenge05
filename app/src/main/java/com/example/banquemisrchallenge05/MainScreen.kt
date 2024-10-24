package com.example.banquemisrchallenge05

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

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
        .padding(bottom = 20.dp, start = 50.dp, end = 50.dp)
        .clip(shape = RoundedCornerShape(25.dp)),
    ) {
        NavigationBar(
            modifier = Modifier.defaultMinSize(minHeight = 45.dp),

            ) {
            items.forEach() {
                //            addBottomNavigationItem(
                //                item = it,
                //                currentDestination = navBackStackEntry?.destination,
                //                NavController
                //            )
                NavigationBarItem(
                    modifier = Modifier.defaultMinSize(minHeight = 35.dp).padding(top = 5.dp),
                    label = { Text(text = it.label) },
                    icon = { Icon(imageVector = it.icon, contentDescription = it.label) },
                    selected = currentRoute == it.route,
                  //  alwaysShowLabel = false,

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
