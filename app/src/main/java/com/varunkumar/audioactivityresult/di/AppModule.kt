package com.varunkumar.audioactivityresult.di

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.varunkumar.audioactivityresult.domain.ApiService
import com.varunkumar.audioactivityresult.domain.MetaDataReader
import com.varunkumar.audioactivityresult.domain.MetaDataReaderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    private const val BASE_URL = "https://deezerdevs-deezer.p.rapidapi.com/"

    @Provides
    @ViewModelScoped
    @Named("mainViewModel")
    fun provideMainPlayer(@ApplicationContext app: Context): Player {
        return ExoPlayer.Builder(app).build()
    }

    @Provides
    @ViewModelScoped
    fun provideMetaDataReader(@ApplicationContext app: Context): MetaDataReader {
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
    @ViewModelScoped
    @Named("apiViewModel")
    fun provideApiPlayer(@ApplicationContext app: Context): Player {
        return ExoPlayer.Builder(app).build()
    }
}