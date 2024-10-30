package com.example.banquemisrchallenge05.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.banquemisrchallenge05.ui.theme.Banquemisrchallenge05Theme
import com.example.banquemisrchallenge05.utils.NetworkObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var networkObserver: NetworkObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Banquemisrchallenge05Theme {
                 networkObserver = NetworkObserver(applicationContext)

                networkObserver.startNetworkCallback()
               MainScreen(networkObserver)

            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        networkObserver.stopNetworkCallback()
    }
}