package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import org.junit.Rule
import org.junit.Test

class WayMarkItemTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun wayMarkItem() {

        // act
        composeTestRule.setContent {
            MaterialTheme {
                WayMarkItem(WayMark.NORMAL)
            }
        }

        // assert
        composeTestRule.onNodeWithText("NORMAL").assertIsDisplayed()
    }

}