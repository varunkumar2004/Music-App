package com.varunkumar.audioactivityresult.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InfoScreen(
    modifier: Modifier,
    artist: String,
//    image: String
) {
    Column(
        modifier = modifier
    ) {
        Button(onClick = { /*TODO*/ }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                Text(text = "Back to Home")
            }
        }


    }
}

@Preview
@Composable
private fun infoPreview() {
    InfoScreen(modifier = Modifier.fillMaxSize(), artist = "Travis Scott")
}