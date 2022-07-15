package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SolutionBookInfo(numberOfBookEntries: Int, totalNumberOfBookEntries: Int) {
    val percentage = calculatePercentage(numberOfBookEntries, totalNumberOfBookEntries)
    val text = "Book entries: $numberOfBookEntries of $totalNumberOfBookEntries ($percentage%)"
    Text(text = text)
}

fun calculatePercentage(current: Int, max: Int): Int {
    return (current.toFloat() / max.toFloat() * 100).toInt()
}

