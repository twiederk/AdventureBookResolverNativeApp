package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class InventoryScreenContentComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_inventory_screen() {
        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                InventoryScreenContent(
                    itemName = "myItemName",
                    itemList = listOf(Item("first item"), Item("second item")),
                    onItemNameChange = { },
                    onAddClick = { },
                    onDeleteClick = { }
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Item Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("myItemName").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add").assertIsDisplayed()
        composeTestRule.onNodeWithText("first item").assertIsDisplayed()
        composeTestRule.onNodeWithText("second item").assertIsDisplayed()
    }

}