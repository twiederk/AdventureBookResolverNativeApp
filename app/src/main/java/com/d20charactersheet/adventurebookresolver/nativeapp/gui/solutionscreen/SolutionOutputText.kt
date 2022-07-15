package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SolutionOutputText(outputText: String) {
    if (outputText.isEmpty()) {
        return
    }
    Text(outputText)
}
