package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class InventoryScreenAppBarComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_inventory_app_bar_screen() {
        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                InventoryScreenAppBar()

            }
        }

        // assert
        composeTestRule.onNodeWithText("Inventory Screen").assertIsDisplayed()
    }
}