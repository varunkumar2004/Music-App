package com.varunkumar.audioactivityresult.domain

import com.varunkumar.audioactivityresult.model.ApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "X-RapidAPI-Key: ed0c169fe1msh474384d08fd3109p1b35adjsn1a350795df6d",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com"
    )
    @GET("search")
    fun getAudio(@Query("q") q: String): Call<ApiData>
}