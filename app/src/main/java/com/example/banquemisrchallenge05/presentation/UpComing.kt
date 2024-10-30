package com.example.banquemisrchallenge05.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.banquemisrchallenge05.viewModel.MoviesViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpComing(navController: NavHostController, movieViewModel: MoviesViewModel) {
    val upComingMovies = movieViewModel.upcomingPager.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding( start = 15.dp),
                text = "Up Coming",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
        },
    ) {
        HorizontalMoviesView(upComingMovies, navController)
    }

}