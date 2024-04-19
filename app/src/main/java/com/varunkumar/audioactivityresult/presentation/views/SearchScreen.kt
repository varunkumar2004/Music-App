package com.varunkumar.audioactivityresult.presentation.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varunkumar.audioactivityresult.data.model.ApiData
import com.varunkumar.audioactivityresult.data.model.Data
import com.varunkumar.audioactivityresult.presentation.ApiViewModel
import com.varunkumar.audioactivityresult.utils.Result
import com.varunkumar.audioactivityresult.utils.extractTime

@Composable
fun SearchScreen(
    modifier: Modifier,
    viewModel: ApiViewModel
) {
    val query by viewModel.search
    val context = LocalContext.current
    val isLoading by viewModel.isLoading
    val result by viewModel.result
    val new_modifier = Modifier.fillMaxWidth()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        OutlinedTextField(
            modifier = new_modifier,
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

        if (isLoading) {
            Box(
                modifier = new_modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            when (result) {
                is Result.Success -> {
                    result.data?.let {
                        MusicItemsColumn(it)
                    }
                }

                is Result.Error -> {
                    Toast.makeText(context, result.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun MusicItemsColumn(
    apiData: ApiData
) {
    var activeMusic by remember {
        mutableStateOf<Data?>(null)
    }

    LazyColumn {
        items(apiData.data) {
            MusicItem(data = it) { clickedData ->
                activeMusic = clickedData
            }
        }
    }
}

@Composable
fun MusicItem(
    data: Data,
    onItemClick: (Data) -> Unit
) {
    val modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(data) }
        .padding(vertical = 10.dp)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = data.title)
            Text(text = data.artist.name, color = Color.DarkGray)
        }

        val time = extractTime(data.duration.toLong())
        Text(text = time)
    }
}

@Preview(showBackground = true)
@Composable
fun Pre() {
//    SearchScreen()

//    MusicItem(data = Data()) {
//
//    }
}