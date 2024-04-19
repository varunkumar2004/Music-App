package com.varunkumar.audioactivityresult.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.varunkumar.audioactivityresult.model.ApiData
import com.varunkumar.audioactivityresult.domain.ApiService
import com.varunkumar.audioactivityresult.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val apiService: ApiService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _search = mutableStateOf(savedStateHandle.get<String>("search") ?: "")
    val search get() = _search

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading

    private val _result = mutableStateOf<Result<ApiData?>>(Result.Success(null))
    val result get() = _result

    fun onTextChange(text: String) {
        _search.value = text
    }

    fun searchData(query: String) {
        _isLoading.value = true
        savedStateHandle["search"] = query
        apiService.getAudio(query).enqueue(object : Callback<ApiData> {
            override fun onResponse(p0: Call<ApiData>, p1: Response<ApiData>) {
                p1.body()?.let { data ->
                    _result.value = Result.Success(data)
                }
            }

            override fun onFailure(p0: Call<ApiData>, p1: Throwable) {
                _result.value = Result.Error(p1.message)
            }
        })
        _isLoading.value = false
    }
}

