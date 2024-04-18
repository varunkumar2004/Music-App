package com.varunkumar.audioactivityresult

import com.varunkumar.audioactivityresult.presentation.views.HomeScreen
import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.varunkumar.audioactivityresult.presentation.ApiViewModel
import com.varunkumar.audioactivityresult.presentation.MainViewModel
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
                SearchScreen(viewModel = apiViewModel)
            }
        }
    }
}



