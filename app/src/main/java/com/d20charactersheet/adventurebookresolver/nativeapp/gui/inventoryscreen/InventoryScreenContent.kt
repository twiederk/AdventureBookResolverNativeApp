package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme


@Composable
fun InventoryScreenContent(
    itemName: String,
    itemList: List<Item>,
    onItemNameChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        AddInventoryItem(itemName, onItemNameChange, onAddClick)
        InventoryItemList(itemList, onDeleteClick)
    }
}


@Composable
private fun AddInventoryItem(itemName: String, onItemNameChange: (String) -> Unit, onAddClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        InventoryItemName(itemName, onItemNameChange)
        AddInventoryItemButton(onAddClick)
    }
}


@Composable
private fun AddInventoryItemButton(onAddClick: () -> Unit) {
    Button(
        onClick = { onAddClick() }
    ) {
        Text("Add")
    }
}


@Composable
private fun InventoryItemName(itemName: String, onItemNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = itemName,
        label = { Text("Item Name") },
        onValueChange = { onItemNameChange(it) }
    )
}


@Composable
fun InventoryItemList(
    itemList: List<Item>,
    onDeleteClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(itemList.size) { index ->
            val item = itemList[index]
            InventoryItemCard(item, index, onDeleteClick)
        }
    }
}


@Composable
private fun InventoryItemCard(
    item: Item,
    index: Int,
    onDeleteClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(item.name)
        ItemDeleteIcon(
            index,
            onDeleteClick = onDeleteClick
        )
    }
}


@Composable
private fun ItemDeleteIcon(index: Int, onDeleteClick: (Int) -> Unit) {
    IconButton(onClick = { onDeleteClick(index) }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "delete",
            tint = Color.Gray
        )
    }
}


@Preview(showBackground = true)
@Composable
fun InventoryScreenContentPreview() {
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
