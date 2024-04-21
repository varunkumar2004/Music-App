package com.varunkumar.audioactivityresult.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.Player
import com.varunkumar.audioactivityresult.domain.ApiService
import com.varunkumar.audioactivityresult.model.ApiData
import com.varunkumar.audioactivityresult.model.AudioItem
import com.varunkumar.audioactivityresult.utils.Result
import com.varunkumar.audioactivityresult.utils.convertToAudioItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val apiService: ApiService,
    @Named("apiViewModel") private val player: Player,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _searchText = mutableStateOf(savedStateHandle.get<String>("search") ?: "")
    val searchText get() = _searchText

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading

    private val _apiResult = mutableStateOf<Result<List<AudioItem>>>(Result.Success(null))
    val apiResult get() = _apiResult

    private val _currPlayer = mutableStateOf<AudioItem?>(null)
    val currPlayer get() = _currPlayer

    var isPlaying = mutableStateOf(player.isPlaying)
        private set

    fun onTextChange(text: String) {
        _searchText.value = text
    }

    fun searchData(query: String) {
        _isLoading.value = true
        savedStateHandle["search"] = query

        apiService.getAudio(query).enqueue(object : Callback<ApiData> {
            override fun onResponse(p0: Call<ApiData>, p1: Response<ApiData>) {
                p1.body()?.let { apiData ->
                    _apiResult.value = Result.Success(convertToAudioItem(apiData))
                }
            }

            override fun onFailure(p0: Call<ApiData>, p1: Throwable) {
                _apiResult.value = Result.Error(p1.message)
            }
        })

        _isLoading.value = false
    }

    init {
        player.prepare()
    }

    fun addAudioUri(item: AudioItem) {
        player.addMediaItem(item.mediaItem)
    }

    fun playAudio(item: AudioItem) {
        if (item != _currPlayer.value) {
            player.setMediaItem(item.mediaItem).also {
                _currPlayer.value = item
            }
        }

        player.play()
        isPlaying.value = true
    }

    fun forwardAudioMillis() {
        val currPosition = player.currentPosition
        player.seekTo(currPosition + 10000)
    }

    fun rewindAudioMillis() {
        val currPosition = player.currentPosition
        player.seekTo(currPosition - 10000)
    }

    fun pauseAudio() {
        player.pause()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}

