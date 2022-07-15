package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class SolutionScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_solution_screen() {
        // arrange
        val game = Game()

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                SolutionScreen(
                    SolutionScreenViewModel(game),
                    EntryScreenViewModel(game),
                    navController = rememberNavController()
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Book entries: 1 of 400 (0%)").assertIsDisplayed()
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