package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation.BottomBar
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun InventoryScreen(
    inventoryScreenViewModel: InventoryScreenViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = { InventoryScreenAppBar() },
        content = {
            InventoryScreenContent(
                itemName = inventoryScreenViewModel.itemName,
                itemList = inventoryScreenViewModel.itemList,
                onItemNameChange = { inventoryScreenViewModel.onItemNameChange(it) },
                onAddClick = { inventoryScreenViewModel.onAddClick() },
                onDeleteClick = { inventoryScreenViewModel.onDeleteClick(it) }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )
}

@Preview
@Composable
fun InventoryScreenPreview() {
    val game = Game()
    game.addItemToInventory("first item")
    game.addItemToInventory("second item")
    AdventureBookResolverTheme {
        InventoryScreen(
            InventoryScreenViewModel(game),
            rememberNavController()
        )
    }
}
