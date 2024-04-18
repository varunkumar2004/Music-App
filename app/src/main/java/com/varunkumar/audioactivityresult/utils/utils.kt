package com.varunkumar.audioactivityresult.utils

sealed class Result<T>(val data: T? = null, val msg: String? = null) {
    class Success<T>(data: T?) : Result<T>(data = data)
    class Error<T>(msg: String?) : Result<T>(msg = msg)
}

fun extractTime(duration: Long): String {
    val hours = duration / 3600
    val minutes = (duration % 3600) / 60
    val remainingSeconds = duration % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

