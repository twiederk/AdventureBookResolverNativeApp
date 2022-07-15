package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class CreateActionScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_create_action_screen_with_error_message() {
        // arrange
        val createActionScreenViewModel = CreateActionScreenViewModel(Game())
        createActionScreenViewModel.onEntryIdChange("100")
        createActionScreenViewModel.onCreateClick()

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                CreateActionScreen(
                    createActionScreenViewModel = createActionScreenViewModel,
                    navController = rememberNavController()
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Create Action Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Action").assertIsDisplayed()
        composeTestRule.onNodeWithText("Id").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
        composeTestRule.onNodeWithText("Can't create action: Label is missing").assertIsDisplayed()
    }

    @Test
    fun should_display_create_action_screen_without_error_message() {
        // arrange
        val createActionScreenViewModel = CreateActionScreenViewModel(Game())
        createActionScreenViewModel.onActionLabelChange("myAction")
        createActionScreenViewModel.onEntryIdChange("100")

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                CreateActionScreen(
                    createActionScreenViewModel = createActionScreenViewModel,
                    navController = rememberNavController()
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Create Action Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Action").assertIsDisplayed()
        composeTestRule.onNodeWithText("myAction").assertIsDisplayed()
        composeTestRule.onNodeWithText("Id").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
        composeTestRule.onNodeWithText("").assertDoesNotExist()
    }

}