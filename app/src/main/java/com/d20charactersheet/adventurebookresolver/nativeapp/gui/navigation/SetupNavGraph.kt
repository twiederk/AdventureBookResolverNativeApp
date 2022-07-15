package com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen.CreateActionScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen.CreateActionScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen.CreateBookScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen.CreateBookScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.BookViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.GraphScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.GraphViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreenViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    bookViewModel: BookViewModel,
    graphViewModel: GraphViewModel,
    load: () -> Unit,
    restart: () -> Unit,
    createBookScreenViewModel: CreateBookScreenViewModel,
    solutionScreenViewModel: SolutionScreenViewModel,
    createActionScreenViewModel: CreateActionScreenViewModel,
    entryScreenViewModel: EntryScreenViewModel,
    inventoryScreenViewModel: InventoryScreenViewModel
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.GraphScreenRoute.route
    ) {
        composable(
            route = ScreenRoute.GraphScreenRoute.route
        ) {
            GraphScreen(
                bookViewModel = bookViewModel,
                graphViewModel = graphViewModel,
                onLoadClick = { load() },
                onRestartClick = { restart() },
                onRenderClick = { /*exportImage()*/ },
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.CreateBookScreenRoute.route
        ) {
            CreateBookScreen(
                createBookScreenViewModel = createBookScreenViewModel,
                bookViewModel = bookViewModel,
                solutionScreenViewModel = solutionScreenViewModel,
                inventoryScreenViewModel = inventoryScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.CreateActionScreen.route
        ) {
            CreateActionScreen(
                createActionScreenViewModel = createActionScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.EntryScreen.route
        ) {
            EntryScreen(
                entryScreenViewModel = entryScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.SolutionScreen.route
        ) {
            SolutionScreen(
                solutionScreenViewModel = solutionScreenViewModel,
                entryScreenViewModel = entryScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.InventoryScreen.route
        ) {
            InventoryScreen(
                inventoryScreenViewModel = inventoryScreenViewModel,
                navController = navController
            )
        }
    }
}