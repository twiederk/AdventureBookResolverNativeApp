package com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
        route = ScreenRoute.SolutionScreenRoute.route,
        title = "Solution",
        icon = Icons.Default.Person
    )

    object Inventory : BottomBarScreen(
        route = ScreenRoute.InventoryScreenRoute.route,
        title = "Inventory",
        icon = Icons.Default.Add
    )
}
