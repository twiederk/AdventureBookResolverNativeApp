package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.BookEntryCard
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.BookEntryViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.SearchResult
import org.junit.Rule
import org.junit.Test

class SearchResultTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun searchResult() {
        // arrange
        val searchResult = listOf(
            BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure"),
            BookEntry(id = 2, title = "Throne")
        )

        // act
        composeTestRule.setContent {
            MaterialTheme {
                SearchResult(searchResult)
            }
        }

        // assert
        composeTestRule.onNodeWithText("(1) - Entry Hall").assertIsDisplayed()
        composeTestRule.onNodeWithText("Start of adventure").assertIsDisplayed()
        composeTestRule.onNodeWithText("(2) - Throne").assertIsDisplayed()
    }

    @Test
    fun bookEntryCard_with_note() {
        // arrange
        val bookEntry = BookEntry(
            id = 1,
            title = "Entry Hall",
            note = "Start of adventure"
        )

        // act
        composeTestRule.setContent {
            MaterialTheme {
                BookEntryCard(BookEntryViewModel(bookEntry))
            }
        }

        // assert
        composeTestRule.onNodeWithText("(1) - Entry Hall").assertIsDisplayed()
        composeTestRule.onNodeWithText("NORMAL").assertIsDisplayed()
        composeTestRule.onNodeWithText("Start of adventure").assertIsDisplayed()
    }

    @Test
    fun bookEntryCard_without_note() {
        // arrange
        val bookEntry = BookEntry(
            id = 1,
            title = "Entry Hall"
        )

        // act
        composeTestRule.setContent {
            MaterialTheme {
                BookEntryCard(BookEntryViewModel(bookEntry))
            }
        }

        // assert
        composeTestRule.onNodeWithText("(1) - Entry Hall").assertIsDisplayed()
    }

}