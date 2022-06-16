package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class SolutionBookEntryCardComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_progress_of_calculation_when_calculation_is_starting() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                SolutionBookEntryCard(BookEntry(1, "Entry Hall"))
            }
        }

        // assert
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Entry Hall").assertIsDisplayed()
    }

}