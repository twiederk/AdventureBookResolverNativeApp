package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class InventoryScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_inventory_screen() {
        // arrange
        val game = Game()
        game.addItemToInventory("first item")

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                InventoryScreen(
                    InventoryScreenViewModel(game),
                    rememberNavController()
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Inventory Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add").assertIsDisplayed()
        composeTestRule.onNodeWithText("first item").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("delete").assertIsDisplayed()
    }

}