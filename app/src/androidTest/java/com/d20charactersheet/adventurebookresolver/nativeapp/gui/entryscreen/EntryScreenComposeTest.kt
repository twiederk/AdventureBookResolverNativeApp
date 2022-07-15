package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class EntryScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_info_of_book_entry() {
        // arrange
        val game = Game()
        game.addAction("First Label", 242)
        game.addAction("Second Label", 339)
        game.addAction("Third Label", 20)

        val entryScreenViewModel = EntryScreenViewModel(game)

        entryScreenViewModel.initBookEntry(
            BookEntry(
                id = 1,
                title = "myTitle",
                note = "myNote",
                visit = Visit.VISITED,
                wayMark = WayMark.WAY_POINT,
            )
        )

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                EntryScreen(
                    entryScreenViewModel = entryScreenViewModel,
                    navController = rememberNavController()
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("myTitle").assertIsDisplayed()
        composeTestRule.onNodeWithText("myNote").assertIsDisplayed()
        composeTestRule.onNodeWithText("WAY_POINT").assertIsDisplayed()
        composeTestRule.onNodeWithText("First Label").assertIsDisplayed()
        composeTestRule.onNodeWithText("242").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second Label").assertIsDisplayed()
        composeTestRule.onNodeWithText("339").assertIsDisplayed()
        composeTestRule.onNodeWithText("Third Label").assertIsDisplayed()
        composeTestRule.onNodeWithText("20").assertIsDisplayed()
        composeTestRule.onNodeWithText("Run To This Entry").assertIsDisplayed()
    }

}