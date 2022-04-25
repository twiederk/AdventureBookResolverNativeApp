package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun SolutionList(solutions: List<Solution>) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        solutions.forEachIndexed { index, solution ->
            SolutionView(index, solution)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun SolutionListPreview() {
    val solutions = listOf(
        Solution(
            solutionPath = listOf(
                BookEntry(1, "Hallway"),
                BookEntry(2, "Throne"),
            )
        ),
        Solution(
            solutionPath = listOf(
                BookEntry(1, "Hallway"),
                BookEntry(2, "Throne"),
                BookEntry(3, "King")
            )
        )
    )

    AdventureBookResolverTheme {
        SolutionList(solutions)
    }
}


@Composable
fun SolutionView(index: Int, solution: Solution) {
    Column {
        Text(text = "Solution ${index + 1} with ${solution.solutionPath.size} entries")
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(checkNotNull(solution.solutionPath)) { bookEntry ->
                SolutionBookEntryCard(bookEntry)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SolutionViewPreview() {
    val solution = Solution(
        solutionPath = listOf(
            BookEntry(1, "Hallway"),
            BookEntry(2, "Throne"),
            BookEntry(3, "King")
        )
    )

    AdventureBookResolverTheme {
        SolutionView(0, solution)
    }
}
