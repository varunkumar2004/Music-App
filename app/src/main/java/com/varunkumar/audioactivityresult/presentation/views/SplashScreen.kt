package com.varunkumar.audioactivityresult.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.varunkumar.audioactivityresult.utils.NavigationBarItems
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Music Player App", fontWeight = FontWeight.Bold)
    }

    LaunchedEffect(true) {
        delay(2000)
        navController.navigate(NavigationBarItems.Home.route)
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
//    SplashScreen()
}