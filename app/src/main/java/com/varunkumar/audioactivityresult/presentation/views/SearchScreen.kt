package com.varunkumar.audioactivityresult.presentation.views

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.varunkumar.audioactivityresult.model.ApiData
import com.varunkumar.audioactivityresult.model.Data
import com.varunkumar.audioactivityresult.presentation.ApiViewModel
import com.varunkumar.audioactivityresult.utils.Result
import com.varunkumar.audioactivityresult.utils.extractTime

@Composable
fun SearchScreen(
    modifier: Modifier,
    viewModel: ApiViewModel
) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading
    val result by viewModel.result

    var selectedItem by remember {
        mutableStateOf<Data?>(null)
    }

    val newModifier = Modifier.fillMaxWidth()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TextField(
            modifier = newModifier,
            value = viewModel.search.value,
            onValueChange = viewModel::onTextChange,
            leadingIcon = {
                IconButton(onClick = { viewModel.searchData(viewModel.search.value) }) {
                    Icon(Icons.Default.Search, contentDescription = "search")
                }
            },
            singleLine = true
        )

        if (isLoading) {
            Box(
                modifier = newModifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            when (result) {
                is Result.Success -> {
                    result.data?.let {
                        Spacer(modifier = Modifier.height(5.dp))
                        MusicItemsColumn(newModifier, it) { data ->
                            selectedItem = data
                        }
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
    modifier: Modifier,
    apiData: ApiData,
    onItemClick: (Data) -> Unit
) {
    LazyColumn {
        items(apiData.data) {
            Item(modifier, data = it) { clickedData ->
                onItemClick(clickedData)
            }
        }
    }
}

@Composable
fun Item(
    modifier: Modifier,
    data: Data,
    onItemClick: (Data) -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = 2.dp, horizontal = 5.dp)
            .clip(RoundedCornerShape(40.dp))
            .border(0.5.dp, Color.LightGray, RoundedCornerShape(40.dp))
            .clickable { onItemClick(data) }
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 10.dp, horizontal = 20.dp),
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
}

@Preview(showBackground = true)
@Composable
fun Pre() {
//    Item("travis scott", "4:23")
}