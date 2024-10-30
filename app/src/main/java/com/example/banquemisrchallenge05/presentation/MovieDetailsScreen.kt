package com.example.banquemisrchallenge05.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.banquemisrchallenge05.R
import com.example.banquemisrchallenge05.data.network.ApiState
import com.example.banquemisrchallenge05.data.model.MovieDetailsResponse
import com.example.banquemisrchallenge05.ui.theme.TransparentRed
import com.example.banquemisrchallenge05.ui.theme.grey
import com.example.banquemisrchallenge05.utils.Constants
import com.example.banquemisrchallenge05.viewModel.MovieDetailsViewModel
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.Shimmer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("SuspiciousIndentation")
@Composable
fun MovieDetailsScreen(
    id: String,
    navController: NavController,
    movieDeatilsViewModel: MovieDetailsViewModel
) {
    LaunchedEffect(id) {
        movieDeatilsViewModel.getMovieDetails(id)
    }
    val movieDetails = movieDeatilsViewModel.movieDetails.collectAsState()
    when (movieDetails.value) {
        is ApiState.Failure -> MovieError(movieDetails)
        is ApiState.Loading -> MovieLoading()
        is ApiState.Success -> {
            MovieDetails(movieDetails.value as ApiState.Success, navController)
           // Text(text = id)
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetails(movieDetails: ApiState.Success, navController: NavController) {
    val movieData = movieDetails.data as MovieDetailsResponse
    val releaseDate = movieData.release_date.let {
        LocalDate.parse(
            it,
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        ).format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)).toString()
    }
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                AsyncImage(
                    model = Constants.IMAGE_BASE_URL + movieData.poster_path,
                    contentDescription = movieData.title,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Button(
                    onClick = { navController.popBackStack() },
                    Modifier.padding(start = 10.dp, top = 10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 5.dp),
                    colors = ButtonDefaults.buttonColors(TransparentRed)
                ) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }

            }


            Text(
                text = movieData.title,
                fontWeight = FontWeight.Bold,
                fontSize = 38.sp,
                lineHeight = 40.sp,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, end = 15.dp)
                    .fillMaxSize(),
            ) {
                Text(
                    text = "Released $releaseDate",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.weight(1f))

                RatingBar(
                    rating = movieData.vote_average.toFloat() / 2,
                    space = 2.dp,
                    imageVectorEmpty = Icons.Default.StarOutline,
                    imageVectorFFilled = Icons.Default.StarRate,
                    tintEmpty = Color.Gray,
                    itemSize = 20.dp,
                    gestureEnabled = false,
                    animationEnabled = true,
                    shimmer = Shimmer(
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 5000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        drawBorder = false,
                    ),
                )
                Text(
                    "(${movieData.vote_count})",
                    fontSize = 14.sp,
                )
            }

            Text("Genres:",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp, top = 5.dp)
            )


               Row(modifier = Modifier.padding(start = 7.dp, end = 7.dp)) {
                     movieData.genres.forEach {
                         Card (modifier = Modifier.padding(horizontal = 5.dp),
                             colors = CardDefaults.cardColors(grey)
                             ) {
                             Text(
                                 text = it.name,
                                 fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 2.dp, bottom = 2.dp)
                             )
                         }
                     }
               }

            Text(
                text = "Overview",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
            )
            Text(
                text = movieData.overview,
                fontSize = 17.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = "Additional Info",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                        append("Runtime:")
                    }
                    withStyle(style = SpanStyle(fontSize = 16.sp, fontStyle = FontStyle.Italic)) {
                        append("${movieData.runtime} minutes")
                    }
                },
                modifier = Modifier.padding(start = 10.dp, top = 5.dp)
            )
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp, end = 20.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 18.sp, fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Budget: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append(if(movieData.budget>0)"${"%,d".format(movieData.budget)}$" else "Unavailable Info")
                        }
                    },
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Revenue: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append(if(movieData.revenue>0)"${"%,d".format(movieData.revenue)}$" else "Unavailable Info")
                        }
                    },
                )

            }

            Text(
                "Production Companies:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp, top = 5.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
            ) {
                movieData.production_companies.forEach {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(start = 10.dp, top = 5.dp)
                    ) {
                        AsyncImage(
                            model = Constants.IMAGE_BASE_URL + it.logo_path,
                            contentDescription = it.name,
                            modifier = Modifier.size(50.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = it.name,
                            fontSize = 13.sp,
                            )
                    }
                }
            }
        }
    }
}


@Composable
fun MovieError(movieDetails: State<ApiState>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(7.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.warning),
            contentDescription = "Error",
            modifier = Modifier.padding(7.dp)
        )
        Text(
            text = (movieDetails.value as ApiState.Failure).message,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun MovieLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        Text(text = "Loading...", color = MaterialTheme.colorScheme.primary)
    }
}