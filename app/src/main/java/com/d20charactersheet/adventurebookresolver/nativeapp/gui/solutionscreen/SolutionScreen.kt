package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun SolutionScreen(
    argument: String,
    onArgumentChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onPathClick: () -> Unit,
    onWayPointClick: () -> Unit,
    onUnvisitedClick: () -> Unit,
    onSolveClick: () -> Unit,
    onRollDieClick: (String) -> Unit,
    onBackClick: () -> Unit,
    bookEntryList: List<BookEntry>,
    onBookEntryClick: (BookEntry) -> Unit,
    actions: List<Action>,
    remainingCombinations: Long,
    maxCombinations: Long,
    numberOfSolutions: Int,
    solutions: List<Solution>,
    outputText: String
) {
    Scaffold(
        topBar = {
            SolutionScreenAppBar(onBackClick = { onBackClick() })
        },
        content = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {

                SolutionCommandPanel(
                    argument = argument,
                    onArgumentChange = { onArgumentChange(it) },
                    onSearchClick = { onSearchClick() },
                    onPathClick = { onPathClick() },
                    onWayPointClick = { onWayPointClick() },
                    onUnvisitedClick = { onUnvisitedClick() },
                    onSolveClick = { onSolveClick() },
                    onRollDieClick = { onRollDieClick(it) },
                )
                BookEntryList(
                    bookEntryList = bookEntryList,
                    onBookEntryClick = { onBookEntryClick(it) }
                )
                ActionList(actions)
                SolutionProgressScreen(
                    remainingCombinations,
                    maxCombinations,
                    numberOfSolutions,
                    solutions
                )
                OutputText(outputText)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SolutionScreenPreview() {
    AdventureBookResolverTheme {
        SolutionScreen(
            argument = "",
            onArgumentChange = { },
            onSearchClick = { },
            onPathClick = { },
            onWayPointClick = { },
            onUnvisitedClick = { },
            onSolveClick = { },
            onRollDieClick = { },
            onBackClick = { },
            bookEntryList = listOf(),
            onBookEntryClick = { },
            actions = listOf(),
            remainingCombinations = 0L,
            maxCombinations = 0L,
            numberOfSolutions = 0,
            solutions = listOf(),
            outputText = ""
        )
    }

}