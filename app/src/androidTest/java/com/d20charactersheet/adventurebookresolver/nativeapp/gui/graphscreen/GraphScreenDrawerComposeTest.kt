package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class GraphScreenDrawerComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_info_of_book_entry() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                GraphScreenDrawer(
                    onCreateClick = { },
                    onLoadClick = { },
                    onRestartClick = { },
                    onRenderClick = { },
                    onImportClick = { },
                    onExportClick = { },
                    onItemClick = { },
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Actions").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
        composeTestRule.onNodeWithText("Load").assertIsDisplayed()
        composeTestRule.onNodeWithText("Restart").assertIsDisplayed()
        composeTestRule.onNodeWithText("Render").assertIsDisplayed()
        composeTestRule.onNodeWithText("Import").assertIsDisplayed()
        composeTestRule.onNodeWithText("Export").assertIsDisplayed()
    }

}