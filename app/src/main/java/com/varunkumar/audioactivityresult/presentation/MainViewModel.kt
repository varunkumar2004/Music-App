package com.varunkumar.audioactivityresult.presentation

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.varunkumar.audioactivityresult.MetaDataReader
import com.varunkumar.audioactivityresult.data.model.AudioItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val player: Player,
    private val metaDataReader: MetaDataReader,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val audioUris = savedStateHandle.getStateFlow("audioUris", emptyList<Uri>())

//    val visiblePermissionDialog = mutableStateListOf<String>()
//
//    fun dismissDialog() {
//        visiblePermissionDialog.removeLast()
//    }
//
//    fun onPermissionResult(
//        permission: String,
//        granted: Boolean
//    ) {
//        if (!granted) {
//            visiblePermissionDialog.add(0, permission)
//        }
//    }

    val audioItems = audioUris.map { uris ->
        uris.map { uri ->
            val metadata = metaDataReader.getMetaDataFromUri(uri)

            AudioItem(
                contentUri = uri,
                mediaItem = MediaItem.fromUri(uri),
                name = metadata?.title ?: uri.lastPathSegment ?: "<Unknown>",
                artist = metadata?.artist ?: "<Unknown>",
                duration = metadata?.duration ?: 0L
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        player.prepare()
    }

    fun addAudioUri(uri: Uri) {
        savedStateHandle["audioUris"] = audioUris.value + uri
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playAudio(uri: Uri) {
        player.setMediaItem(
            audioItems.value.find { it.contentUri == uri }?.mediaItem ?: return
        )
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}