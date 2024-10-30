package com.example.banquemisrchallenge05.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.banquemisrchallenge05.ui.theme.Banquemisrchallenge05Theme
import com.example.banquemisrchallenge05.utils.NetworkObserver
import com.example.banquemisrchallenge05.viewModel.MovieDetailsViewModel
import com.example.banquemisrchallenge05.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var networkObserver: NetworkObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Banquemisrchallenge05Theme {

                val movieDeatilsViewModel: MovieDetailsViewModel = hiltViewModel()
                val movieViewModel : MoviesViewModel = hiltViewModel()
                networkObserver = NetworkObserver(applicationContext)
                networkObserver.startNetworkCallback()
               MainScreen(networkObserver,movieViewModel,movieDeatilsViewModel)

            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        networkObserver.stopNetworkCallback()
    }
}