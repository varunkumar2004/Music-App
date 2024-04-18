package com.varunkumar.audioactivityresult.data.model

data class ApiData(
    val `data`: List<Data>,
    val next: String,
    val total: Int
)