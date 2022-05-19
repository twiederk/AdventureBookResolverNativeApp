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
                    command = Command.Create,
                    argument = "",
                    onCommandChange = { },
                    onArgumentChange = { },
                    onExecuteClick = { },
                    onPathClick = { },
                    onWayPointClick = { },
                    onUnvisitedClick = { },
                    onSolveClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Command").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
        composeTestRule.onNodeWithText("Argument").assertIsDisplayed()
        composeTestRule.onNodeWithText("Execute").assertIsDisplayed()
        composeTestRule.onNodeWithText("Path").assertIsDisplayed()
        composeTestRule.onNodeWithText("Way Points").assertIsDisplayed()
        composeTestRule.onNodeWithText("Unvisited").assertIsDisplayed()
        composeTestRule.onNodeWithText("Solve").assertIsDisplayed()
    }

}