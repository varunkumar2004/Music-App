package com.varunkumar.audioactivityresult.di

import android.app.Application
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.varunkumar.audioactivityresult.domain.MetaDataReader
import com.varunkumar.audioactivityresult.domain.MetaDataReaderImpl
import com.varunkumar.audioactivityresult.domain.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    private const val BASE_URL = "https://deezerdevs-deezer.p.rapidapi.com/"

    @Provides
    // make this scoped for whole application
    @ViewModelScoped
    @Named("mainViewModelPlayer")
    fun provideAudioPlayer(app: Application): Player {
        return ExoPlayer.Builder(app).build()
    }

    @Provides
    @ViewModelScoped
    fun provideMetaDataReader(app: Application): MetaDataReader {
        return MetaDataReaderImpl(app)
    }

    @Provides
    @ViewModelScoped
    fun provideApiService(): ApiService {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("appPlayer")
    fun providePlayer(app: Application): Player {
        return ExoPlayer.Builder(app).build()
    }
}