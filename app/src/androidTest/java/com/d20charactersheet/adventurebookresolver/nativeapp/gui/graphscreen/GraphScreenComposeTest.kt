package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class GraphScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_info_of_book_entry() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                GraphScreen(
                    title = "myTitle",
                    onZoomChange = { },
                    onSaveClick = { },
                    onCreateClick = { },
                    onLoadClick = { },
                    onRestartClick = { },
                    onRenderClick = { },
                    onFabClick = { },
                    onEntryTouch = { },
                    scale = 1F
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("myTitle").assertIsDisplayed()
    }

}