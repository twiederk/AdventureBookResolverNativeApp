package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

class InventoryScreenViewModel(private val game: Game) : ViewModel() {

    var itemList by mutableStateOf(game.getItems())
        private set

    var itemName by mutableStateOf("")
        private set

    fun onItemNameChange(itemName: String) {
        this.itemName = itemName
    }

    fun onAddClick() {
        if (itemName.isNotEmpty()) {
            game.addItemToInventory(itemName)
            itemList = game.getItems()
            itemName = ""
        }
    }

    fun onDeleteClick(index: Int) {
        game.removeItemFromInventory(index)
        itemList = game.getItems()
    }
}
