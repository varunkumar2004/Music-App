package com.varunkumar.audioactivityresult.data.model

import android.net.Uri
import androidx.media3.common.MediaItem

data class AudioItem(
    val contentUri: Uri,
    val mediaItem: MediaItem,
    val name: String,
    val artist: String,
    val duration: Long
)