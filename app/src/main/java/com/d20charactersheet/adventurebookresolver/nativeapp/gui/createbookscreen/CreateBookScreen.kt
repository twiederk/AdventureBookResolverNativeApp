package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.BookViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.MEDIUM_PADDING

@Composable
fun CreateBookScreen(
    createBookScreenViewModel: CreateBookScreenViewModel,
    bookViewModel: BookViewModel,
    solutionScreenViewModel: SolutionScreenViewModel,
    inventoryScreenViewModel: InventoryScreenViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CreateBookScreenAppBar(
                onBackClick = { navController.popBackStack() }
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(all = MEDIUM_PADDING)

            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = createBookScreenViewModel.title,
                    onValueChange = { createBookScreenViewModel.onTitleChange(it) },
                    label = { Text("Title") }
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        createBookScreenViewModel.onCreateClick()
                        bookViewModel.onTitleChange(createBookScreenViewModel.title)
                        solutionScreenViewModel.clear()
                        inventoryScreenViewModel.reset()
                        navController.popBackStack()
                    }) {
                    Text("Create")
                }
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun CreateBookScreenPreview() {
    val game = Game()
    AdventureBookResolverTheme {
        CreateBookScreen(
            createBookScreenViewModel = CreateBookScreenViewModel(game),
            bookViewModel = BookViewModel(game),
            solutionScreenViewModel = SolutionScreenViewModel(game),
            inventoryScreenViewModel = InventoryScreenViewModel(game),
            navController = rememberNavController()
        )
    }
}