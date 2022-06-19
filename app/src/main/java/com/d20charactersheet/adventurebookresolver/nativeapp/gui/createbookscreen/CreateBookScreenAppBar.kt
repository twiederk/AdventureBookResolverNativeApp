package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.BackAction

@Composable
fun CreateBookScreenAppBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = { BackAction(onBackClick = { onBackClick() }) },
        title = {
            Text(text = "Create Book Screen")
        }
    )

}