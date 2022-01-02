package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand.WayMarkDropDown

@Composable
fun SearchResult(searchResult: List<BookEntry>) {
    Column {
        for (bookEntry in searchResult) {
            BookEntryCard(BookEntryViewModel(bookEntry))
        }
    }
}

@Preview
@Composable
fun SearchResultPreview() {
    val searchResult = listOf(
        BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure"),
        BookEntry(id = 2, title = "Throne")
    )
    MaterialTheme {
        SearchResult(searchResult)
    }
}

@Composable
fun BookEntryCard(bookEntryViewModel: BookEntryViewModel) {
    Surface(
        modifier = Modifier.padding(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            Text(
                text = "(${bookEntryViewModel.id}) - ${bookEntryViewModel.title}"
            )
            WayMarkDropDown(
                modifier = Modifier.padding(vertical = 8.dp),
                wayMark = bookEntryViewModel.wayMark,
                onWayMarkSelected = { bookEntryViewModel.onWayMarkSelected(it) }
            )
            if (bookEntryViewModel.note.isNotEmpty()) {
                Text(bookEntryViewModel.note)
            }
        }
    }
}

@Preview
@Composable
fun BookEntryCardPreview() {
    val bookEntry = BookEntry(
        id = 1,
        title = "Entry Hall",
        note = "Start of adventure"
    )
    MaterialTheme {
        BookEntryCard(BookEntryViewModel(bookEntry))
    }
}