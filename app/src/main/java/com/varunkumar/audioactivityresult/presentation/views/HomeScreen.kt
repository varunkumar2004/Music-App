package com.varunkumar.audioactivityresult.presentation.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.varunkumar.audioactivityresult.model.AudioItem
import com.varunkumar.audioactivityresult.presentation.SearchViewModel
import com.varunkumar.audioactivityresult.utils.NavigationBarItems
import com.varunkumar.audioactivityresult.utils.Result
import com.varunkumar.audioactivityresult.utils.extractTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: SearchViewModel,
    navController: NavController
) {
    navController.clearBackStack(NavigationBarItems.Splash.route)
    val context = LocalContext.current

    val isLoading by viewModel.isLoading.collectAsState()
    val result by viewModel.apiResult
    val currPlayer by viewModel.currPlayer
    val isPlaying by viewModel.isPlaying

    var showAlert by remember {
        mutableStateOf(false)
    }

    var selectedIcon by remember {
        mutableStateOf(NavigationBarItems.Home.label)
    }

    val newModifier = Modifier.fillMaxWidth()

    Scaffold(
        topBar = {
            currPlayer?.let {
                TopBar(
                    isPlaying = isPlaying,
                    currPlayer = it,
                    onPlayPauseClick = { if (!isPlaying) viewModel.playAudio(it) else viewModel.pauseAudio() },
                    seekBackMillis = { viewModel.rewindAudioMillis() },
                    seekForwardMillis = { viewModel.forwardAudioMillis() },
                    onInfoClick = {
                        showAlert = true
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(TextFieldDefaults.MinHeight + 10.dp),
            ) {
                NavigationBarItem(
                    alwaysShowLabel = false,
                    selected = selectedIcon == NavigationBarItems.Home.label,
                    onClick = {
                        selectedIcon = NavigationBarItems.Home.label
                    },
                    icon = {
                        Icon(
                            imageVector = NavigationBarItems.Home.icon,
                            contentDescription = null
                        )
                    }
                )

                NavigationBarItem(
                    alwaysShowLabel = false,
                    selected = selectedIcon == NavigationBarItems.Local.label,
                    onClick = {
                        navController.navigate(NavigationBarItems.Local.route)
                    },
                    icon = {
                        Icon(
                            imageVector = NavigationBarItems.Local.icon,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextField(
                modifier = newModifier,
                value = viewModel.searchText.value,
                onValueChange = viewModel::onTextChange,
                leadingIcon = {
                    IconButton(onClick = { viewModel.searchData(viewModel.searchText.value) }) {
                        Icon(Icons.Default.Search, contentDescription = "search")
                    }
                },
                singleLine = true
            )

            if (isLoading) {
                Box(
                    modifier = newModifier,
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                when (result) {
                    is Result.Success -> {
                        result.data?.let { items ->
                            Spacer(modifier = Modifier.height(5.dp))

                            MusicItemsColumn(newModifier, items) { data ->
                                viewModel.addAudioUri(data)
                                viewModel.playAudio(data)
                            }
                        }
                    }

                    is Result.Error -> {
                        Toast.makeText(context, result.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    if (showAlert) {
        AlertDialog(onDismissRequest = { showAlert = false }) {
            currPlayer?.let { item ->
                InfoAlert(
                    modifier = Modifier.fillMaxWidth(),
                    item = item
                ) { request ->
                    showAlert = false
                    viewModel.searchData(request)
                }
            }
        }
    }
}

@Composable
fun InfoAlert(
    modifier: Modifier,
    item: AudioItem,
    onItemClick: (String) -> Unit
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
                .padding(20.dp)
                .clickable { onItemClick(item.artist!!.name) },
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
                .padding(20.dp)
                .clickable { onItemClick(item.album!!.title) },
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


@Composable
fun MusicItemsColumn(
    modifier: Modifier,
    items: List<AudioItem>,
    onItemClick: (AudioItem) -> Unit
) {
    LazyColumn {
        items(items) {
            Item(modifier, item = it) { clickedData ->
                onItemClick(clickedData)
            }
        }
    }
}

@Composable
fun Item(
    modifier: Modifier,
    item: AudioItem,
    onItemClick: (AudioItem) -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = 2.dp, horizontal = 5.dp)
            .clip(RoundedCornerShape(40.dp))
            .border(0.5.dp, Color.LightGray, RoundedCornerShape(40.dp))
            .clickable { onItemClick(item) }
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(0.7f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item.cover?.let {cover ->
                    AsyncImage(
                        model = cover,
                        contentDescription = "cover",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                }

                Column {
                    Text(
                        text = item.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.artist?.name ?: "Unknown",
                        color = Color.DarkGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            val time = extractTime(item.duration)
            Text(text = time)
        }
    }
}

@Composable
fun TopBar(
    isPlaying: Boolean,
    currPlayer: AudioItem,
    onInfoClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    seekBackMillis: () -> Unit,
    seekForwardMillis: () -> Unit,
) {
    val modifier = Modifier.fillMaxWidth()

    Row(
        modifier = modifier
            .background(Color.Black)
            .padding(10.dp)
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // display cover if present
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(0.5f)
                .clickable { onInfoClick() }
        ) {
            AsyncImage(
                model = currPlayer.cover,
                contentDescription = "cover",
                modifier = Modifier
                    .size(maxOf(50.dp, 50.dp))
                    .clip(RoundedCornerShape(5.dp))
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = currPlayer.name,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = currPlayer.artist?.name ?: "Unknown",
                    maxLines = 1,
                    color = Color.LightGray,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        MusicPlayerView(
            isPlaying = isPlaying,
            onPlayPauseClick = { onPlayPauseClick() },
            seekBackMillis = { seekBackMillis() },
            seekForwardMillis = { seekForwardMillis() }
        )
    }
}

@Composable
fun MusicPlayerView(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    seekBackMillis: () -> Unit,
    seekForwardMillis: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { seekBackMillis() }) {
            Icon(
                imageVector = Icons.Default.Replay10,
                contentDescription = null,
                tint = Color.White
            )
        }

        IconButton(onClick = { onPlayPauseClick() }) {
            Icon(
                imageVector =
                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White
            )
        }

        IconButton(onClick = { seekForwardMillis() }) {
            Icon(
                imageVector = Icons.Default.Forward10,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
