package com.varunkumar.audioactivityresult.domain

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import androidx.core.database.getStringOrNull

class MetaDataReaderImpl(
    private val app: Application
) : MetaDataReader {
    override fun getMetaDataFromUri(contentUri: Uri): MetaData? {
        if (contentUri.scheme != "content") return null

        val projection = arrayOf(
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST
        )

        var file: MetaData? = null

        app.contentResolver.query(
            contentUri,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val nameCol = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
            val artistCol = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val durationCol = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)

            cursor.moveToFirst()

            val title = cursor.getStringOrNull(nameCol) ?: ""
            val artist = cursor.getString(artistCol) ?: "<Unknown>"
            val duration = cursor.getLong(durationCol)

            file = MetaData(title, artist, duration)
        }

        return file
    }
}
