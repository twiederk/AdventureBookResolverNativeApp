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
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreen
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreenViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    bookViewModel: BookViewModel,
    graphViewModel: GraphViewModel,
    save: () -> Unit,
    load: () -> Unit,
    restart: () -> Unit,
    createBookScreenViewModel: CreateBookScreenViewModel,
    solutionScreenViewModel: SolutionScreenViewModel,
    createActionScreenViewModel: CreateActionScreenViewModel,
    entryScreenViewModel: EntryScreenViewModel
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.GraphScreenRoute.route
    ) {
        composable(
            route = ScreenRoute.GraphScreenRoute.route
        ) {
            GraphScreen(
                title = bookViewModel.title,
                onZoomChange = { graphViewModel.onScaleChange(it) },
                onSaveClick = { save() },
                onCreateClick = { navController.navigate(ScreenRoute.CreateBookScreenRoute.route) },
                onLoadClick = { load() },
                onRestartClick = { restart() },
                onSolutionClick = { navController.navigate(ScreenRoute.SolutionScreen.route) },
                onRenderClick = { /*exportImage()*/ },
                onFabClick = { navController.navigate(ScreenRoute.CreateActionScreen.route) },
                onEntryTouch = { navController.navigate(ScreenRoute.EntryScreen.route) },
                scale = graphViewModel.scale
            )
        }
        composable(
            route = ScreenRoute.CreateBookScreenRoute.route
        ) {
            CreateBookScreen(
                title = createBookScreenViewModel.title,
                onTitleChange = { createBookScreenViewModel.onTitleChange(it) },
                onCreateClick = {
                    createBookScreenViewModel.onCreateClick()
                    bookViewModel.onTitleChange(createBookScreenViewModel.title)
                    solutionScreenViewModel.clear()
                    navController.popBackStack()
                },
                onCancelClick = { navController.popBackStack() }
            )
        }
        composable(
            route = ScreenRoute.CreateActionScreen.route
        ) {
            CreateActionScreen(
                actionLabel = createActionScreenViewModel.actionLabel,
                entryId = createActionScreenViewModel.entryId,
                errorMessage = createActionScreenViewModel.errorMessage,
                onActionLabelChange = { createActionScreenViewModel.onActionLabelChange(it) },
                onEntryIdChange = { createActionScreenViewModel.onEntryIdChange(it) },
                onCancelClick = { navController.popBackStack() },
                onCreateClick = {
                    if (createActionScreenViewModel.onCreateClick()) {
                        navController.popBackStack()
                    }
                }
            )
        }
        composable(
            route = ScreenRoute.EntryScreen.route
        ) {
            EntryScreen(
                id = entryScreenViewModel.id,
                title = entryScreenViewModel.title,
                note = entryScreenViewModel.note,
                visit = entryScreenViewModel.visit,
                wayMark = entryScreenViewModel.wayMark,
                actions = entryScreenViewModel.actions,
                onTitleChanged = { entryScreenViewModel.onTitleChanged(it) },
                onNoteChanged = { entryScreenViewModel.onNoteChanged(it) },
                onWayMarkSelected = { entryScreenViewModel.onWayMarkSelected(it) },
                onActionMoveClicked = {
                    entryScreenViewModel.onActionMoveClicked(it)
                    navController.popBackStack()
                },
                onActionDeleteClicked = { entryScreenViewModel.onActionDeleteClicked(it) },
                onBackNavigationClicked = { navController.popBackStack() }
            )
        }
        composable(
            route = ScreenRoute.SolutionScreen.route
        ) {
            SolutionScreen(
                argument = solutionScreenViewModel.argument,
                onArgumentChange = { solutionScreenViewModel.onArgumentChange(it) },
                onSearchClick = { solutionScreenViewModel.onSearchClick() },
                onPathClick = { solutionScreenViewModel.onPathClick() },
                onWayPointClick = { solutionScreenViewModel.onWayPointClick() },
                onUnvisitedClick = { solutionScreenViewModel.onUnvisitedClick() },
                onSolveClick = { solutionScreenViewModel.onSolveClick() },
                onRollDieClick = { solutionScreenViewModel.onDieRollClick(it) },
                onBackClick = { navController.popBackStack() },
                bookEntryList = solutionScreenViewModel.bookEntryList,
                onBookEntryClick = {
                    entryScreenViewModel.initBookEntry(it)
                    navController.navigate(ScreenRoute.EntryScreen.route)
                },
                actions = solutionScreenViewModel.actionList,
                remainingCombinations = solutionScreenViewModel.remainingCombinations,
                maxCombinations = solutionScreenViewModel.maxCombinations,
                numberOfSolutions = solutionScreenViewModel.numberOfSolutions,
                solutions = solutionScreenViewModel.solutionList,
                outputText = solutionScreenViewModel.outputText

            )
        }
    }
}