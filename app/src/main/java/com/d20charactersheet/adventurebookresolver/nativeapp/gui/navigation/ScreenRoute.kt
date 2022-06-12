package com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation

sealed class ScreenRoute(val route: String) {
    object GraphScreenRoute : ScreenRoute(route = "graph_screen_route")
    object CreateBookScreenRoute : ScreenRoute(route = "create_book_screen_route")
    object CreateActionScreen : ScreenRoute(route = "create_action_screen_route")
    object EntryScreen : ScreenRoute(route = "entry_screen_route")
}
