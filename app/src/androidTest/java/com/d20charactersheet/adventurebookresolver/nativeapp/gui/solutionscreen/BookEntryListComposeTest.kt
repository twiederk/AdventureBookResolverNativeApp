package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class BookEntryListComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_list_of_book_entries() {
        // arrange
        val bookEntryList = listOf(
            BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure"),
            BookEntry(id = 2, title = "Throne")
        )

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                BookEntryList(
                    bookEntryList = bookEntryList,
                    onBookEntryClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("1")
        composeTestRule.onNodeWithText("Entry Hall").assertIsDisplayed()
        composeTestRule.onNodeWithText("Start of adventure").assertIsDisplayed()
        composeTestRule.onNodeWithText("2")
        composeTestRule.onNodeWithText("Throne").assertIsDisplayed()
    }

    @Test
    fun should_display_book_entry_with_note() {
        // arrange
        val bookEntry = BookEntry(
            id = 1,
            title = "Entry Hall",
            note = "Start of adventure"
        )

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                BookEntryCard(
                    bookEntry = bookEntry,
                    onBookEntryClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("1")
        composeTestRule.onNodeWithText("Entry Hall").assertIsDisplayed()
        composeTestRule.onNodeWithText("Start of adventure").assertIsDisplayed()
    }

    @Test
    fun should_display_book_entry_without_note() {
        // arrange
        val bookEntry = BookEntry(
            id = 1,
            title = "Entry Hall"
        )

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                BookEntryCard(
                    bookEntry = bookEntry,
                    onBookEntryClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("1")
        composeTestRule.onNodeWithText("Entry Hall").assertIsDisplayed()
    }

}