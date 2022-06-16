package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class GraphScreenAppBarComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_info_of_book_entry() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                GraphScreenAppBar(
                    title = "myTitle",
                    onZoomChange = { },
                    onSaveClick = { },
                    onCreateClick = { },
                    onLoadClick = { },
                    onRestartClick = { },
                    onSolutionClick = { },
                    onRenderClick = { },
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("myTitle").assertIsDisplayed()
        composeTestRule.onNode(hasTestTag("save_icon")).assertContentDescriptionEquals("Save")
        composeTestRule.onNode(hasTestTag("actions_drop_down_icon"))
            .assertContentDescriptionEquals("Actions Drop Down")
        composeTestRule.onNode(hasTestTag("zoom_drop_down_icon"))
            .assertContentDescriptionEquals("Zoom Drop Down")
    }

}