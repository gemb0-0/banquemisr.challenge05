package com.example.banquemisrchallenge05.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.banquemisrchallenge05.data.model.Movie
import com.example.banquemisrchallenge05.utils.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.absoluteValue


@Composable
fun HorizontalMoviesView(
    movieList: LazyPagingItems<Movie>,
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
                Log.i("Popular", "MovieList: $it")
                val releaseDate = it.release_date.let {
                    LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
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
                                navController.navigate("movieDetails/${it.id}")
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}