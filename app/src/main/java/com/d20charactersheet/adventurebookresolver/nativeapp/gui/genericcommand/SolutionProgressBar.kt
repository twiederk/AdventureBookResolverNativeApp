package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import java.text.DecimalFormat

@Composable
fun SolutionProgressBar(remainingCombinations: Long, maxCombinations: Long) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RemainingCombinationsText(
            modifier = Modifier.weight(1F),
            remainingCombinations = remainingCombinations
        )
        SolutionProgressIndicator(
            modifier = Modifier.weight(3F),
            remainingCombinations = remainingCombinations,
            maxCombinations = maxCombinations
        )
        MaxCombinationsText(
            modifier = Modifier.weight(1F),
            maxCombinations = maxCombinations
        )
    }
}


@Composable
private fun RemainingCombinationsText(modifier: Modifier, remainingCombinations: Long) {
    val numberFormatter = remember { DecimalFormat("#,###") }
    Text(modifier = modifier, text = numberFormatter.format(remainingCombinations), textAlign = TextAlign.End)
}


@Composable
private fun SolutionProgressIndicator(modifier: Modifier, remainingCombinations: Long, maxCombinations: Long) {
    LinearProgressIndicator(
        modifier = modifier.padding(20.dp),
        progress = calculateProgress(remainingCombinations, maxCombinations),
    )
}

private fun calculateProgress(remainingCombinations: Long, maxCombinations: Long): Float {
    if (maxCombinations == 0L) return 0F
    return remainingCombinations.toFloat() / maxCombinations.toFloat()
}


@Composable
private fun MaxCombinationsText(modifier: Modifier, maxCombinations: Long) {
    val numberFormatter = remember { DecimalFormat("#,###") }
    Text(modifier = modifier, text = numberFormatter.format(maxCombinations), textAlign = TextAlign.End)
}


@Preview
@Composable
fun SolutionProgressBarPreview() {
    AdventureBookResolverTheme {
        SolutionProgressBar(200, 10000)
    }
}
