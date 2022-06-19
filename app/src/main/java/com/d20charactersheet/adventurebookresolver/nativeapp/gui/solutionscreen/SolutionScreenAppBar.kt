package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme


@Composable
fun SolutionScreenAppBar() {
    TopAppBar(
        title = {
            Text(text = "Solution Screen")
        }
    )
}


@Composable
fun BackAction(
    onBackClick: () -> Unit
) {
    IconButton(onClick = { onBackClick() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
        )
    }
}


@Preview
@Composable
fun SolutionScreenAppBarPreview() {
    AdventureBookResolverTheme {
        SolutionScreenAppBar()
    }
}