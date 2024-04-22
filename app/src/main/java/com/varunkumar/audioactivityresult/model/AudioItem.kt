package com.varunkumar.audioactivityresult.model

import android.net.Uri
import androidx.media3.common.MediaItem

data class AudioItem (
    val isLocal: Boolean,
    val contentUri: String,
    val mediaItem: MediaItem,
    val name: String,
    val duration: Long,
    val cover: String?,
    val album: Album? = null,
    val artist: Artist? = null
)