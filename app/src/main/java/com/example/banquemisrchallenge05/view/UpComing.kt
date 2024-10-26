package com.example.banquemisrchallenge05.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UpComing(){

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(top = 40.dp, start = 15.dp),
                text = "Up Coming",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
        },

        ) {


    }

}