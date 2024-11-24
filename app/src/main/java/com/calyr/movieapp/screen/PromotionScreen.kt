package com.calyr.movieapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun PromotionScreen(title: String) {
    Scaffold() {
        paddingValues -> PromotionContent(modifier = Modifier.padding(paddingValues),
            title)
    }
}

@Composable
fun PromotionContent(modifier: Modifier, titlePromotion: String){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(titlePromotion)
    }
}