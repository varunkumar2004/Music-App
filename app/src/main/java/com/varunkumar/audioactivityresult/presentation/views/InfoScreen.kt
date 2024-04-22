package com.varunkumar.audioactivityresult.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.varunkumar.audioactivityresult.model.AudioItem

@Composable
fun InfoScreen(
    modifier: Modifier,
    item: AudioItem
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Black)
            .padding(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = item.name,
                color = Color.White
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.artist!!.picture_medium,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.DarkGray)
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${item.artist.name} (${item.artist.type})",
                color = Color.White,
                textAlign = TextAlign.End
            )
        }

        Divider(color = Color.DarkGray)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.album!!.cover_big,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray)
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${item.album.title} (${item.album.type})",
                color = Color.White,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun infoPreview() {
//    InfoScreen(modifier = Modifier.fillMaxSize(), artist = "Travis Scott")
}