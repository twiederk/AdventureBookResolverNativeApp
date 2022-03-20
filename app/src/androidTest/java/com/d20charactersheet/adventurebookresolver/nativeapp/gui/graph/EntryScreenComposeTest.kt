package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
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
        val actions = listOf(
            Action(
                label = "First Label",
                source = BookEntry(id = 182, title = "First Source Title"),
                destination = BookEntry(id = 242, wayMark = WayMark.NORMAL, visit = Visit.VISITED)
            ),
            Action(
                label = "Second Label",
                source = BookEntry(id = 169, title = "Second Source Title"),
                destination = BookEntry(id = 339, wayMark = WayMark.WAY_POINT, visit = Visit.UNVISITED)
            ),
            Action(
                label = "Third Label",
                source = BookEntry(id = 29, title = "Third Source Title"),
                destination = BookEntry(id = 20, wayMark = WayMark.DEAD_END, visit = Visit.UNVISITED)
            )
        )

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                EntryScreen(
                    title = "myTitle",
                    note = "myNote",
                    wayMark = WayMark.WAY_POINT,
                    actions = actions,

                    onTitleChanged = { },
                    onNoteChanged = { },
                    onWayMarkSelected = { },
                    onActionMoveClicked = { },
                    onActionDeleteClicked = { },
                    onBackNavigationClicked = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("myTitle").assertIsDisplayed()
        composeTestRule.onNodeWithText("myNote").assertIsDisplayed()
        composeTestRule.onNodeWithText("WAY_POINT").assertIsDisplayed()
        composeTestRule.onNodeWithText("First Label").assertIsDisplayed()
        composeTestRule.onNodeWithText("242").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second Label").assertIsDisplayed()
        composeTestRule.onNodeWithText("339").assertIsDisplayed()
        composeTestRule.onNodeWithText("Third Label").assertIsDisplayed()
        composeTestRule.onNodeWithText("20").assertIsDisplayed()
    }

}