package com.varunkumar.audioactivityresult

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.varunkumar.audioactivityresult.presentation.ApiViewModel
import com.varunkumar.audioactivityresult.presentation.MainViewModel
import com.varunkumar.audioactivityresult.presentation.views.HomeScreen
import com.varunkumar.audioactivityresult.presentation.views.SearchScreen
import com.varunkumar.audioactivityresult.ui.theme.AudioActivityResultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(UnstableApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO implement all permission for various android versions
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            0
        )

        setContent {
            AudioActivityResultTheme {
                val mainViewModel = hiltViewModel<MainViewModel>()
                val apiViewModel = hiltViewModel<ApiViewModel>()

//                val dialogQueue = viewModel.visiblePermissionDialog
//
//                val audioPermissionResultLauncher =
//                    rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
//                        viewModel.onPermissionResult(
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            isGranted
//                        )
//                    }
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Button(onClick = { audioPermissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) }) {
//                        Text(text = "Request one permission")
//                    }
//                }
//                HomeScreen(viewModel = mainViewModel)
                var selectedIcon by remember {
                    mutableStateOf(NavigationBarItems.Home.label)
                }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                alwaysShowLabel = false,
                                selected = selectedIcon == NavigationBarItems.Home.label,
                                onClick = {
                                    selectedIcon = NavigationBarItems.Home.label
                                },
                                icon = {
                                    Icon(
                                        imageVector = NavigationBarItems.Home.icon,
                                        contentDescription = null
                                    )
                                }
                            )

                            NavigationBarItem(
                                alwaysShowLabel = false,
                                selected = selectedIcon == NavigationBarItems.Search.label,
                                onClick = {
                                    selectedIcon = NavigationBarItems.Search.label
                                },
                                icon = {
                                    Icon(
                                        imageVector = NavigationBarItems.Search.icon,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                ) {
                    val modifier = Modifier.padding(it)
                    if (selectedIcon == NavigationBarItems.Home.label) {
                        HomeScreen(modifier = modifier, viewModel = mainViewModel)
                    } else if (selectedIcon == NavigationBarItems.Search.label) {
                        SearchScreen(modifier = modifier, viewModel = apiViewModel)
                    }
                }
            }
        }
    }
}

sealed class NavigationBarItems(
    val label: String,
    val icon: ImageVector
) {
    data object Home : NavigationBarItems(label = "Home", icon = Icons.Default.Home)
    data object Search : NavigationBarItems(label = "Search", icon = Icons.Default.Search)
}
