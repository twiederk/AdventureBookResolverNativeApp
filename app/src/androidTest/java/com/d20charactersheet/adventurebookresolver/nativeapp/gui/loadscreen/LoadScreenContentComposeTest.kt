package com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class LoadScreenContentComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_load_screen_content() {
        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                LoadScreenContent(
                    fileNames = listOf("first book.abr"),
                    onLoadClick = { },
                    onDeleteClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("first book.abr").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("delete").assertIsDisplayed()
    }

}