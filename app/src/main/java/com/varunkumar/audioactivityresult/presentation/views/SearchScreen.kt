package com.varunkumar.audioactivityresult.presentation.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varunkumar.audioactivityresult.data.model.ApiData
import com.varunkumar.audioactivityresult.data.model.Data
import com.varunkumar.audioactivityresult.presentation.ApiViewModel
import com.varunkumar.audioactivityresult.utils.Result

@Composable
fun SearchScreen(
    viewModel: ApiViewModel = hiltViewModel()
) {
    val query by viewModel.search

    val result by viewModel.result
    val modifier = Modifier.fillMaxWidth()
    
    Column(
        modifier = modifier
            .padding(10.dp, 20.dp)
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = query,
            onValueChange = viewModel::onTextchange,
            label = { Text(text = "Enter artist") },
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.searchData(query)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            },
        )


        Column {
            when (result) {
                is Result.Success -> {
                    Text(text = "Success")
                    Log.d("SearchScreen", result.data.toString())
                }

                is Result.Error -> {
                    Text(text = "Error")
                    Log.d("SearchScreen", result.msg ?: "error")
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun MusicItemsColumn(
    apiData: ApiData
) {
    LazyColumn {
        items(apiData.data) {
            
        }
    }
}

@Composable
fun MusicItem(
    data: Data
) {
    
}

@Preview(showBackground = true)
@Composable
fun pre() {
    SearchScreen()
}