package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class WayMarkDropDownComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun wayMarkDropDown() {

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                WayMarkDropDown(
                    wayMark = WayMark.NORMAL,
                    onWayMarkSelected = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("NORMAL").assertIsDisplayed()
    }

}