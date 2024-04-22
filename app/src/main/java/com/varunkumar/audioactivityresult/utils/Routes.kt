package com.varunkumar.audioactivityresult.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationBarItems(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Splash : NavigationBarItems(
        route = "splash_screen",
        label = "Splash",
        icon = Icons.Default.Home
    )

    data object Home :
        NavigationBarItems(route = "home_screen", label = "Home", icon = Icons.Default.Home)

    data object Local : NavigationBarItems(
        route = "local_screen",
        label = "Local",
        icon = Icons.Default.FilePresent
    )
}
