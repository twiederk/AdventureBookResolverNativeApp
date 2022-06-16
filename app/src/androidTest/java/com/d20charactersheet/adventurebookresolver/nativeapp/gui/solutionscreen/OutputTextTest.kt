package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test


class OutputTextTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_output_text() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                OutputText("myOutputText")
            }
        }

        // assert
        composeTestRule.onNodeWithText("myOutputText").assertIsDisplayed()
    }

    @Test
    fun should_not_exist_when_text_is_empty() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                OutputText("")
            }
        }

        // assert
        composeTestRule.onNodeWithText("").assertDoesNotExist()
    }

}