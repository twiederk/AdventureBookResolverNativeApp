package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import java.text.DecimalFormat

@Composable
fun SolutionProgressBar(remainingCombinations: Long, maxCombinations: Long, numberOfSolutions: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        RemainingCombinationsText(
            remainingCombinations = remainingCombinations
        )
        SolutionProgressIndicator(
            remainingCombinations = remainingCombinations,
            maxCombinations = maxCombinations,
            numberOfSolutions = numberOfSolutions
        )
        SolutionFoundText(
            numberOfSolutions = numberOfSolutions
        )
    }
}


@Composable
private fun RemainingCombinationsText(modifier: Modifier = Modifier, remainingCombinations: Long) {
    val numberFormatter = remember { DecimalFormat("#,###") }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Remaining permutations:")
        Text(
            modifier = modifier,
            text = numberFormatter.format(remainingCombinations),
            textAlign = TextAlign.End
        )
    }
}


@Composable
private fun SolutionProgressIndicator(modifier: Modifier = Modifier, remainingCombinations: Long, maxCombinations: Long, numberOfSolutions: Int) {
    val color = if (numberOfSolutions == 0) Color.Red else Color.Green
    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        color = color,
        progress = calculateProgress(remainingCombinations, maxCombinations),
    )
}

private fun calculateProgress(remainingCombinations: Long, maxCombinations: Long): Float {
    if (maxCombinations == 0L) return 0F
    return 1F - (remainingCombinations.toFloat() / maxCombinations.toFloat())
}

@Composable
fun SolutionFoundText(numberOfSolutions: Int) {
    Text("Found solutions: $numberOfSolutions")
}


@Preview(showBackground = true, name = "No solution")
@Composable
fun SolutionProgressBarNoSolutionPreview() {
    AdventureBookResolverTheme {
        SolutionProgressBar(
            remainingCombinations = 8000,
            maxCombinations = 10000,
            numberOfSolutions = 0
        )
    }
}

@Preview(showBackground = true, name = "With Solution")
@Composable
fun SolutionProgressBarWithSolutionPreview() {
    AdventureBookResolverTheme {
        SolutionProgressBar(
            remainingCombinations = 2000,
            maxCombinations = 10000,
            numberOfSolutions = 1
        )
    }
}
