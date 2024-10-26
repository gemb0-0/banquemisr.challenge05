package com.example.banquemisrchallenge05.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.banquemisrchallenge05.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_ViewModelComponentBuilder

//@Preview

@Composable
fun Popular() {
    val viewModel = hiltViewModel<MovieViewModel>()
    viewModel.getPopularMovies()
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(top = 40.dp, start = 15.dp),
                text = "popular",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
        },

        ) {


    }

}