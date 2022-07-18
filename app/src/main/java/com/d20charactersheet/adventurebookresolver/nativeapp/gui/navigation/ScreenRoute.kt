package com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation

sealed class ScreenRoute(val route: String) {
    object GraphScreenRoute : ScreenRoute(route = "graph_screen_route")
    object CreateBookScreenRoute : ScreenRoute(route = "create_book_screen_route")
    object CreateActionScreenRoute : ScreenRoute(route = "create_action_screen_route")
    object EntryScreenRoute : ScreenRoute(route = "entry_screen_route")
    object SolutionScreenRoute : ScreenRoute(route = "solution_screen_route")
    object InventoryScreenRoute : ScreenRoute(route = "inventory_screen_route")
    object LoadScreenRoute : ScreenRoute(route = "load_screen_route")
}
