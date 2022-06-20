package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen

import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class InventoryScreenViewModelTest {

    private val inventoryScreenViewModel = InventoryScreenViewModel(Game())

    @Test
    fun `should init with empty item name`() {

        // act
        val name = inventoryScreenViewModel.itemName

        // assert
        assertThat(name).isEmpty()
    }

    @Test
    fun `should update item name`() {

        // act
        inventoryScreenViewModel.onItemNameChange("new item name")

        // assert
        assertThat(inventoryScreenViewModel.itemName).isEqualTo("new item name")
    }

    @Test
    fun `should add item to inventory`() {
        // arrange
        inventoryScreenViewModel.onItemNameChange("item name")

        // act
        inventoryScreenViewModel.onAddClick()

        // assert
        assertThat(inventoryScreenViewModel.itemList).containsExactly(Item("item name"))
        assertThat(inventoryScreenViewModel.itemName).isEmpty()
    }

    @Test
    fun `should not add an item to inventory when item name is empty`() {

        // act
        inventoryScreenViewModel.onAddClick()

        // assert
        assertThat(inventoryScreenViewModel.itemList).isEmpty()
    }

    @Test
    fun `should contain list of items`() {

        // act
        val itemList = inventoryScreenViewModel.itemList

        // assert
        assertThat(itemList).isEmpty()
    }

    @Test
    fun `should delete item from list of items`() {
        // arrange
        inventoryScreenViewModel.onItemNameChange("myItem")
        inventoryScreenViewModel.onAddClick()

        // act
        inventoryScreenViewModel.onDeleteClick(0)

        // assert
        assertThat(inventoryScreenViewModel.itemList).isEmpty()
    }

}