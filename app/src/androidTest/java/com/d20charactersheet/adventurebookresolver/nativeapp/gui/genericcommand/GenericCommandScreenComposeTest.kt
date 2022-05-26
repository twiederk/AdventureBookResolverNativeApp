package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class GenericCommandScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_generic_command_screen() {
        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                GenericCommandScreen(
                    argument = "",
                    onArgumentChange = { },
                    onSearchClick = { },
                    onPathClick = { },
                    onWayPointClick = { },
                    onUnvisitedClick = { },
                    onSolveClick = { },
                    onRollDieClick = { }
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