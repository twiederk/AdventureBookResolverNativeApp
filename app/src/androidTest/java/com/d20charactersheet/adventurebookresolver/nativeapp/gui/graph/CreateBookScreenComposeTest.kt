package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class CreateBookScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_create_book_screen() {
        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                CreateBookScreen(
                    title = "myTitle",
                    onTitleChange = { },
                    onCreateClick = { },
                    onCancelClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("myTitle").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
    }

}