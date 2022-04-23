package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class SolutionViewComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_solution_with_entries() {
        // arrange
        val solution = Solution(
            solutionPath = listOf(
                BookEntry(1, "Hallway"),
                BookEntry(2, "Throne"),
                BookEntry(3, "King")
            )
        )

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                SolutionView(0, solution)
            }
        }

        // assert
        composeTestRule.onNodeWithText("Solution 1 with 3 entries").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hallway").assertIsDisplayed()
        composeTestRule.onNodeWithText("2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Throne").assertIsDisplayed()
        composeTestRule.onNodeWithText("3").assertIsDisplayed()
        composeTestRule.onNodeWithText("King").assertIsDisplayed()
    }

}