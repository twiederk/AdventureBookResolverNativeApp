package com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun LoadScreen(
    loadScreenViewModel: LoadScreenViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            LoadScreenAppBar(
                onBackClick = { navController.popBackStack() }
            )
        },
        content = {
            LoadScreenContent(
                fileNames = loadScreenViewModel.fileNames,
                onLoadClick = {
                    loadScreenViewModel.loadBook(it)
                    navController.popBackStack()
                },
                onDeleteClick = { loadScreenViewModel.deleteBook(it) }
            )
        }
    )

}

