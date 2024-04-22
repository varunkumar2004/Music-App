package com.varunkumar.audioactivityresult.utils

import androidx.media3.common.MediaItem
import com.varunkumar.audioactivityresult.model.ApiData
import com.varunkumar.audioactivityresult.model.AudioItem

sealed class Result<T>(val data: T? = null, val msg: String? = null) {
    class Success<T>(data: T?) : Result<T>(data = data)
    class Error<T>(msg: String?) : Result<T>(msg = msg)
}

fun extractTime(duration: Long): String {
    val hours = duration / 3600
    val minutes = (duration % 3600) / 60
    val remainingSeconds = duration % 60
    val result = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)

    return if (result.substring(0, 2) == "00") {
        result.substring(3)
    } else {
        result
    }
}

fun convertToAudioItem(apiData: ApiData): List<AudioItem> {
    val items = mutableListOf<AudioItem>()

    apiData.data.map { data ->
        items.add(
            AudioItem(
                isLocal = false,
                contentUri = data.preview,
                mediaItem = MediaItem.fromUri(data.preview),
                name = data.title,
                duration = data.duration.toLong(),
                cover = data.album.cover,
                artist = data.artist,
                album = data.album
            )
        )
    }

    return items
}
