package com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class LoadScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_load_screen() {
        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                LoadScreen(
                    LoadScreenViewModel(Game()),
                    rememberNavController()
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Load Book Screen").assertIsDisplayed()
    }

}