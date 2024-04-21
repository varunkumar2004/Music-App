package com.varunkumar.audioactivityresult.presentation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.varunkumar.audioactivityresult.domain.MetaDataReader
import com.varunkumar.audioactivityresult.model.AudioItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LocalViewModel @Inject constructor(
    @Named("mainViewModel") val player: Player,
    private val metaDataReader: MetaDataReader,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val audioUris = savedStateHandle.getStateFlow("audioUris", emptyList<Uri>())

    val audioItems = audioUris.map { uris ->
        uris.map { uri ->
            val metadata = metaDataReader.getMetaDataFromUri(uri)
            val strUri = uri.toString()

            AudioItem(
                isLocal = true,
                contentUri = strUri,
                mediaItem = MediaItem.fromUri(uri),
                name = metadata?.title ?: uri.lastPathSegment ?: "<Unknown>",
                artist = metadata?.artist ?: "<Unknown>",
                duration = metadata?.duration ?: 0L,
                cover = null
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

    fun playAudio(uri: String) {
        player.setMediaItem(
            audioItems.value.find {
                it.contentUri == uri
            }?.mediaItem ?: return
        )
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}