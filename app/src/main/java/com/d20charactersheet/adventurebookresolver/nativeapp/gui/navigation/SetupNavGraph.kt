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
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.GraphScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.GraphScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen.LoadScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen.LoadScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreenViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    graphScreenViewModel: GraphScreenViewModel,
    restart: () -> Unit,
    import: () -> Unit,
    createBookScreenViewModel: CreateBookScreenViewModel,
    solutionScreenViewModel: SolutionScreenViewModel,
    createActionScreenViewModel: CreateActionScreenViewModel,
    entryScreenViewModel: EntryScreenViewModel,
    inventoryScreenViewModel: InventoryScreenViewModel,
    loadScreenViewModel: LoadScreenViewModel
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.GraphScreenRoute.route
    ) {
        composable(
            route = ScreenRoute.GraphScreenRoute.route
        ) {
            GraphScreen(
                graphScreenViewModel = graphScreenViewModel,
                onRestartClick = { restart() },
                onRenderClick = { /*exportImage()*/ },
                onImportClick = { import() },
                navController = navController,
            )
        }
        composable(
            route = ScreenRoute.CreateBookScreenRoute.route
        ) {
            CreateBookScreen(
                createBookScreenViewModel = createBookScreenViewModel,
                solutionScreenViewModel = solutionScreenViewModel,
                inventoryScreenViewModel = inventoryScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.CreateActionScreenRoute.route
        ) {
            CreateActionScreen(
                createActionScreenViewModel = createActionScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.EntryScreenRoute.route
        ) {
            EntryScreen(
                entryScreenViewModel = entryScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.SolutionScreenRoute.route
        ) {
            SolutionScreen(
                solutionScreenViewModel = solutionScreenViewModel,
                entryScreenViewModel = entryScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.InventoryScreenRoute.route
        ) {
            InventoryScreen(
                inventoryScreenViewModel = inventoryScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.LoadScreenRoute.route
        ) {
            LoadScreen(
                loadScreenViewModel = loadScreenViewModel,
                navController = navController
            )
        }
    }
}