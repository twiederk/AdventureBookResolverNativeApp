package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class SolutionProgressScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_progress_bar_and_solution() {

        // arrange
        val solutions = listOf(
            Solution(
                solutionPath = listOf(
                    BookEntry(1, "Hallway"),
                    BookEntry(2, "Throne"),
                    BookEntry(3, "King")
                )
            )
        )


        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                SolutionProgressScreen(
                    remainingCombinations = 200,
                    maxCombinations = 500,
                    numberOfSolutions = 1,
                    solutions = solutions
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("200").assertIsDisplayed()
        composeTestRule.onNodeWithText("Found solutions: 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Solution 1 with 3 entries").assertIsDisplayed()
    }

    @Test
    fun should_hide_progress_bar_when_max_combinations_is_zero() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                SolutionProgressScreen(1, 0, 0, listOf())
            }
        }

        // assert
        composeTestRule.onNodeWithText("0").assertDoesNotExist()
        composeTestRule.onNodeWithText("1").assertDoesNotExist()
    }

}