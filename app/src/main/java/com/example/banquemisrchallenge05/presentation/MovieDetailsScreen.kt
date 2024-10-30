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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.banquemisrchallenge05.R
import com.example.banquemisrchallenge05.data.model.Genre
import com.example.banquemisrchallenge05.data.network.ApiState
import com.example.banquemisrchallenge05.data.model.MovieDetailsResponse
import com.example.banquemisrchallenge05.ui.theme.TransparentRed
import com.example.banquemisrchallenge05.ui.theme.grey
import com.example.banquemisrchallenge05.utils.Constants
import com.example.banquemisrchallenge05.viewModel.MovieDetailsViewModel
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.Shimmer

@SuppressLint("SuspiciousIndentation")
@Composable
fun MovieDetailsScreen(
    id: String,
    navController: NavController,
    movieDetailsViewModel: MovieDetailsViewModel
) {
    LaunchedEffect(id) {
        movieDetailsViewModel.getMovieDetails(id)
    }
    val movieDetails = movieDetailsViewModel.movieDetails.collectAsState()
    when (movieDetails.value) {
        is ApiState.Failure -> MovieError(movieDetails)
        is ApiState.Loading -> MovieLoading()
        is ApiState.Success -> MovieDetails(movieDetails.value as ApiState.Success, navController)

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetails(movieDetails: ApiState.Success, navController: NavController) {
    val movieData = movieDetails.data as MovieDetailsResponse

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                SetImg(movieData.poster_path, Modifier.fillMaxSize())
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

            SetMovieTitle(movieData.title, 5.dp, 10.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, end = 15.dp)
                    .fillMaxSize(),
            ) {

                SetReleaseDate(movieData.release_date)

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

            SetText("Genres:", 25.sp)


            SetGenres(movieData.genres)

            SetText("Overview:", 25.sp)
            Text(
                text = movieData.overview,
                fontSize = 17.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            SetText("Additional Info:", 25.sp)

            SetAnnotatedStrings(movieData.runtime.toString(), "Runtime:", Modifier.padding(start = 10.dp))

            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp, end = 20.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SetAnnotatedStrings(movieData.budget.toString(), "Budget:")

                SetAnnotatedStrings(movieData.revenue.toString(), "Revenue:")

            }

            SetText("Production Companies:")

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
                        SetImg(it.logo_path, Modifier.size(35.dp))
                        SetText(it.name, 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun SetGenres(Genre: List<Genre>) {
    Row(modifier = Modifier.padding(start = 7.dp, end = 7.dp)) {
        Genre.forEach {
            Card(
                modifier = Modifier.padding(horizontal = 5.dp),
                colors = CardDefaults.cardColors(grey)
            ) {
                Text(
                    text = it.name,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 2.dp,
                        bottom = 2.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun SetAnnotatedStrings(movieData: String, title: String, modifier: Modifier = Modifier.padding(start = 0.dp)) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Bold
                )
            ) {
                append(title)
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,

                )
            ) {
                append(
                    when {
                        title == "Runtime:" -> "$movieData minutes"
                        movieData.toInt() > 0 -> "${"%,d".format(movieData.toInt())}$"
                        else -> "Unavailable Info"
                    }
                )
            }
        },
    )
}

@Composable
private fun SetText(txt: String, fontSize: TextUnit = 18.sp) {
    Text(
        text = txt,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 10.dp, top = 5.dp)
    )
}

@Composable
fun SetImg(endPoint: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = Constants.IMAGE_BASE_URL + endPoint,
        contentDescription = endPoint,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
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