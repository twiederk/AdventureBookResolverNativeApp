package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.BackAction
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme


@Composable
fun CreateActionScreenAppBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = { BackAction(onBackClick = { onBackClick() }) },
        title = {
            Text(text = "Create Action Screen")
        }
    )
}


@Preview
@Composable
fun SolutionScreenAppBarPreview() {
    AdventureBookResolverTheme {
        CreateActionScreenAppBar(
            onBackClick = { }
        )
    }
}