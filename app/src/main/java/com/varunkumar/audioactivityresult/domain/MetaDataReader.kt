package com.varunkumar.audioactivityresult.domain

import android.net.Uri

interface MetaDataReader {
    fun getMetaDataFromUri(contentUri: Uri): MetaData?
}
