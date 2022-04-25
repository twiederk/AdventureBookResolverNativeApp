package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun SolutionScreen(
    remainingCombinations: Long,
    maxCombinations: Long,
    numberOfSolutions: Int,
    solutions: List<Solution>
) {
    if (maxCombinations == 0L) {
        return
    }
    Column {
        SolutionProgressBar(remainingCombinations, maxCombinations, numberOfSolutions)
        SolutionList(solutions)
    }
}


@Preview
@Composable
fun SolutionScreen() {
    val solutions = listOf(
        Solution(
            solutionPath = listOf(
                BookEntry(1, "Hallway"),
                BookEntry(2, "Throne"),
                BookEntry(3, "King")
            )
        ),
        Solution(
            solutionPath = listOf(
                BookEntry(10, "Entry Hall"),
                BookEntry(20, "Library"),
            )
        )
    )


    AdventureBookResolverTheme {
        SolutionScreen(
            remainingCombinations = 2000,
            maxCombinations = 10000,
            numberOfSolutions = 0,
            solutions = solutions
        )
    }

}
