package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class SolutionListComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_progress_of_calculation_when_calculation_is_starting() {

        // arrange
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

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                SolutionList(solutions)
            }
        }

        // assert
        composeTestRule.onNodeWithText("Found 2 solution(s)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Solution 1 with 3 entries").assertIsDisplayed()
        composeTestRule.onNodeWithText("Solution 2 with 2 entries").assertIsDisplayed()
    }

}