package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class SolutionBookInfoComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_output_text() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                SolutionBookInfo(
                    numberOfBookEntries = 40,
                    totalNumberOfBookEntries = 400
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Book entries: 40 of 400 (10%)").assertIsDisplayed()
    }

}