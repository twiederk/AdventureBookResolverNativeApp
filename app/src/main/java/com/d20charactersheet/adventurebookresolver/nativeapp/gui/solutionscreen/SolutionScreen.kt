package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation.BottomBar
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation.ScreenRoute
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun SolutionScreen(
    solutionScreenViewModel: SolutionScreenViewModel,
    entryScreenViewModel: EntryScreenViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            SolutionScreenAppBar()
        },
        content = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                SolutionBookInfo(
                    numberOfBookEntries = solutionScreenViewModel.numberOfBookEntries,
                    totalNumberOfBookEntries = solutionScreenViewModel.totalNumberOfBookEntries
                )
                SolutionCommandPanel(
                    argument = solutionScreenViewModel.argument,
                    onArgumentChange = { solutionScreenViewModel.onArgumentChange(it) },
                    onSearchClick = { solutionScreenViewModel.onSearchClick() },
                    onPathClick = { solutionScreenViewModel.onPathClick() },
                    onWayPointClick = { solutionScreenViewModel.onWayPointClick() },
                    onUnvisitedClick = { solutionScreenViewModel.onUnvisitedClick() },
                    onSolveClick = { solutionScreenViewModel.onSolveClick() },
                    onRollDieClick = { solutionScreenViewModel.onDieRollClick(it) },
                )
                BookEntryList(
                    bookEntryList = solutionScreenViewModel.bookEntryList,
                    onBookEntryClick = {
                        entryScreenViewModel.initBookEntry(it)
                        navController.navigate(ScreenRoute.EntryScreenRoute.route)
                    }
                )
                ActionList(solutionScreenViewModel.actionList)
                SolutionProgressScreen(
                    solutionScreenViewModel.remainingCombinations,
                    solutionScreenViewModel.maxCombinations,
                    solutionScreenViewModel.numberOfSolutions,
                    solutionScreenViewModel.solutionList
                )
                SolutionOutputText(solutionScreenViewModel.outputText)
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SolutionScreenPreview() {
    val game = Game()
    AdventureBookResolverTheme {
        SolutionScreen(
            SolutionScreenViewModel(game),
            EntryScreenViewModel(game),
            navController = rememberNavController()
        )
    }

}