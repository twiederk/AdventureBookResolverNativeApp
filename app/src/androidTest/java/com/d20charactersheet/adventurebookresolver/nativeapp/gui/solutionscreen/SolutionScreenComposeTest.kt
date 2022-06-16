package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class SolutionScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_solution_screen() {
        // act
        composeTestRule.setContent {
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

        // assert
        composeTestRule.onNodeWithText("Argument").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
        composeTestRule.onNodeWithText("Path").assertIsDisplayed()
        composeTestRule.onNodeWithText("Way Points").assertIsDisplayed()
        composeTestRule.onNodeWithText("Unvisited").assertIsDisplayed()
        composeTestRule.onNodeWithText("Solve").assertIsDisplayed()
        composeTestRule.onNodeWithText("Roll 1d6").assertIsDisplayed()
        composeTestRule.onNodeWithText("Roll 2d6").assertIsDisplayed()
    }

}