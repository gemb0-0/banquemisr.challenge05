package com.example.banquemisrchallenge05.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.banquemisrchallenge05.viewModel.MoviesViewModel

@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Popular(navController: NavHostController) {
    val viewModel = hiltViewModel<MoviesViewModel>()

    val popularMovies = viewModel.popularPager.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = "popular",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) {
/*
        Column {
            Spacer(modifier = Modifier.padding(40.dp))
            LazyColumn {
                items(popularMovies) { movie ->
                    movie?.let {
                        Text(
                            text = it.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
                when {
                    popularMovies.loadState.append is LoadState.Loading -> {
                        item {
                            Row () {
                                CircularProgressIndicator(modifier = Modifier.padding(10.dp))
                            }

                        }
                    }
                    popularMovies.loadState.append is LoadState.Error -> {
                        val e = popularMovies.loadState.append as LoadState.Error
                        Log.e("Popular", "Error loading more data: ${e.error}")
                    }
                    popularMovies.loadState.append is LoadState.NotLoading -> {
                        Log.i("Popular", "No more items to load")
                    }
                }
            }

        }
*/
        HorizontalMoviesView(popularMovies, navController)
    }

}

