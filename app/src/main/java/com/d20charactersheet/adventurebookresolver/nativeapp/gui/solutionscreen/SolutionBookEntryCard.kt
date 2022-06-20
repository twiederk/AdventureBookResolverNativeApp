package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun SolutionBookEntryCard(bookEntry: BookEntry) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BookEntryId(bookEntry)
        EntryTitle(bookEntry)
    }
}

@Composable
private fun EntryTitle(bookEntry: BookEntry) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp),
        text = bookEntry.title
    )
}

@Preview(showBackground = true)
@Composable
fun SolutionBookEntryCardPreview() {
    AdventureBookResolverTheme {
        SolutionBookEntryCard(BookEntry(1, "Entry Hall"))
    }
}
