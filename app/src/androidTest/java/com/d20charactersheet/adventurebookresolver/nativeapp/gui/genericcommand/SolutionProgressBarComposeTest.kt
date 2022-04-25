package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class SolutionProgressBarComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_progress_of_calculation_when_calculation_is_starting() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                SolutionProgressBar(2000, 10000, 0)
            }
        }

        // assert
        composeTestRule.onNodeWithText("Remaining permutations:").assertIsDisplayed()
        composeTestRule.onNodeWithText("2.000").assertIsDisplayed()
        composeTestRule.onNodeWithText("Found Solutions: 0")
    }

}