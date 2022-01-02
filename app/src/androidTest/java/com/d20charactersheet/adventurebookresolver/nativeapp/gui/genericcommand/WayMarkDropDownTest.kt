package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import org.junit.Rule
import org.junit.Test

class WayMarkDropDownTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun wayMarkDropDown() {

        // act
        composeTestRule.setContent {
            MaterialTheme {
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