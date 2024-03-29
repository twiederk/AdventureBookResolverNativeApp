package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class EntryIdComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_id_of_book_entry() {
        // arrange


        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                BookEntryId(BookEntry(id = 1))
            }
        }

        // assert
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
    }

}