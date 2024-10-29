package com.example.banquemisrchallenge05.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.banquemisrchallenge05.model.Movie
import com.example.banquemisrchallenge05.utils.Constants
import com.example.banquemisrchallenge05.viewModel.MoviesViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.absoluteValue

@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Popular(navController: NavHostController) {
    val viewModel = hiltViewModel<MoviesViewModel>()

    val popularMovies = viewModel.popularPager.collectAsLazyPagingItems()

    Scaffold(
                topBar = {
            Text(
                modifier = Modifier.padding( start = 15.dp),
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
        MovieList(popularMovies, viewModel, navController)
    }

}

@Composable
fun MovieList(
    movieList: LazyPagingItems<Movie>,
    viewModel: MoviesViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState { movieList.itemCount }
        HorizontalPager(
            state = pagerState, beyondViewportPageCount = 3,
            contentPadding = PaddingValues(45.dp),

        )
        { page ->
            movieList[page]?.let {
                Log.i("Popular", "MovieList: ${it}")
                val releaseDate = it.release_date.let {
                    LocalDate.parse(
                        it,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
                    ).format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH))
                }


                Card(
                    modifier = Modifier.padding(10.dp),
                    colors = CardDefaults.cardColors(Color.Transparent),
                ) {
                    Text(
                        text = it.title,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 30.dp)
                    )

                    Text(
                        text = "Released $releaseDate",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(start = 35.dp)
                    )
                    AsyncImage(
                        model = Constants.IMAGE_BASE_URL + it.poster_path,
                        contentDescription = it.title,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(50.dp))
                            .aspectRatio(2f / 3f)
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                val zooming = lerp(
                                    1f, 1.25f, pageOffset
                                )
                                scaleX *= zooming
                                scaleY *= zooming

                            }
                            .clickable {
                                //  viewModel.getMovieDetails(it.id)
                                // Log.i("Popular", "MovieId: ${it.id}")
                                navController.navigate("movieDetails/${it.id}")
                            }, contentScale = ContentScale.Crop

                    )
                }
            }
        }
    }
}