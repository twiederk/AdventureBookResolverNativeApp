package com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Graph : BottomBarScreen(
        route = ScreenRoute.GraphScreenRoute.route,
        title = "Graph",
        icon = Icons.Default.Home
    )

    object Solution : BottomBarScreen(
        route = ScreenRoute.SolutionScreen.route,
        title = "Solution",
        icon = Icons.Default.Person
    )
}
