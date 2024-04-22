package com.varunkumar.audioactivityresult.model

data class ApiData(
    val `data`: List<ListData>,
    val next: String,
    val total: Int
)