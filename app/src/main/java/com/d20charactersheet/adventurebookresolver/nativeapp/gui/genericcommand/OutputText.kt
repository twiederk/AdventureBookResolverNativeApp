package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun OutputText(text: String) {
    if (text.isEmpty()) {
        return
    }
    Text(text)
}
