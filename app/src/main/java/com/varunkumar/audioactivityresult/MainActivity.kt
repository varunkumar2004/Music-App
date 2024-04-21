package com.varunkumar.audioactivityresult

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.varunkumar.audioactivityresult.presentation.ApiViewModel
import com.varunkumar.audioactivityresult.presentation.LocalViewModel
import com.varunkumar.audioactivityresult.utils.NavigationBarItems
import com.varunkumar.audioactivityresult.presentation.views.HomeScreen
import com.varunkumar.audioactivityresult.presentation.views.LocalScreen
import com.varunkumar.audioactivityresult.presentation.views.SplashScreen
import com.varunkumar.audioactivityresult.ui.theme.AudioActivityResultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                val navController = rememberNavController()
                val localViewModel = hiltViewModel<LocalViewModel>()
                val apiViewModel = hiltViewModel<ApiViewModel>()

                val modifier = Modifier
                NavHost(
                    navController = navController,
                    startDestination = NavigationBarItems.Splash.route
                ) {
                    composable(NavigationBarItems.Splash.route) {
                        SplashScreen(navController = navController)
                    }
                    composable(NavigationBarItems.Home.route) {
                        HomeScreen(modifier = modifier, viewModel = apiViewModel, navController = navController)
                    }
                    composable(NavigationBarItems.Local.route) {
                        LocalScreen(modifier = modifier, viewModel = localViewModel)
                    }
                }
            }
        }
    }
}

