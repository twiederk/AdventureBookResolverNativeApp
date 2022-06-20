package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun InventoryScreenAppBar() {
    TopAppBar(
        title = {
            Text(text = "Inventory Screen")
        }
    )
}

@Preview
@Composable
fun InventoryScreenAppBarPreview() {
    AdventureBookResolverTheme {
        InventoryScreenAppBar()
    }
}
