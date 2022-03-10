package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

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

class ActionListTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_info_of_action() {
        // arrange
        val action = Action(
            label = "myLabel",
            source = BookEntry(id = 182, title = "mySourceTitle"),
            destination = BookEntry(id = 242, wayMark = WayMark.NORMAL, visit = Visit.UNVISITED)
        )

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                ActionCard(action)
            }
        }

        // assert
        composeTestRule.onNodeWithText("182").assertIsDisplayed()
        composeTestRule.onNodeWithText("mySourceTitle").assertIsDisplayed()
        composeTestRule.onNodeWithText("myLabel").assertIsDisplayed()
        composeTestRule.onNodeWithText("242").assertIsDisplayed()
    }

    @Test
    fun should_display_list_with_info_of_all_actions() {
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
        )

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                ActionList(actions)
            }
        }

        // assert
        composeTestRule.onNodeWithText("182").assertIsDisplayed()
        composeTestRule.onNodeWithText("First Source Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("First Label").assertIsDisplayed()
        composeTestRule.onNodeWithText("242").assertIsDisplayed()

        composeTestRule.onNodeWithText("169").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second Source Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second Label").assertIsDisplayed()
        composeTestRule.onNodeWithText("339").assertIsDisplayed()
    }

}