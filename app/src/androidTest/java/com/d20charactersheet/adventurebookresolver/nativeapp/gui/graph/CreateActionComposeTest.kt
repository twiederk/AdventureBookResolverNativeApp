package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class CreateActionComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_create_action_screen_with_error_message() {
        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                CreateActionScreen(
                    actionLabel = "myAction",
                    entryId = "100",
                    errorMessage = "myErrorMessage",
                    onActionLabelChange = { },
                    onEntryIdChange = { },
                    onCancelClick = { },
                    onCreateClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Action").assertIsDisplayed()
        composeTestRule.onNodeWithText("myAction").assertIsDisplayed()
        composeTestRule.onNodeWithText("Id").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
        composeTestRule.onNodeWithText("myErrorMessage").assertIsDisplayed()
    }

    @Test
    fun should_display_create_action_screen_without_error_message() {
        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                CreateActionScreen(
                    actionLabel = "myAction",
                    entryId = "100",
                    errorMessage = "",
                    onActionLabelChange = { },
                    onEntryIdChange = { },
                    onCancelClick = { },
                    onCreateClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Action").assertIsDisplayed()
        composeTestRule.onNodeWithText("myAction").assertIsDisplayed()
        composeTestRule.onNodeWithText("Id").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
        composeTestRule.onNodeWithText("").assertDoesNotExist()
    }

}