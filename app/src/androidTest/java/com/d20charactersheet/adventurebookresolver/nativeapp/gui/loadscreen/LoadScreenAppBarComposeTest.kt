package com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class LoadScreenAppBarComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_load_screen_app_bar() {
        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                LoadScreenAppBar(
                    onBackClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Load Book Screen").assertIsDisplayed()
    }

}