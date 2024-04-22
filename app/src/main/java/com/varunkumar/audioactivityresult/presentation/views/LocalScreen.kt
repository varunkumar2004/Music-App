package com.varunkumar.audioactivityresult.presentation.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.PlayerView
import com.varunkumar.audioactivityresult.presentation.LocalViewModel
import com.varunkumar.audioactivityresult.utils.extractTime

@Composable
fun LocalScreen(
    modifier: Modifier,
    viewModel: LocalViewModel
) {
    val audioItems by viewModel.audioItems.collectAsState()

    val selectAudioLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let(viewModel::addAudioUri)
        }

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = modifier
    ) {
        AndroidView(
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .fillMaxWidth(),
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> it.player?.pause()
                    Lifecycle.Event.ON_RESUME -> it.onResume()
                    else -> Unit
                }
            },
            factory = { context ->
                PlayerView(context).apply {
                    player = viewModel.player
                }
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        IconButton(
            onClick = { selectAudioLauncher.launch("audio/*") }
        ) {
            Icon(imageVector = Icons.Default.FileOpen, contentDescription = null)
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(audioItems) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.playAudio(item.contentUri)
                        }
                        .padding(vertical = 10.dp, horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Item(modifier = Modifier.fillMaxWidth(), item = item) {
                        // TODO now selected track
                    }
                }
            }
        }
    }
}