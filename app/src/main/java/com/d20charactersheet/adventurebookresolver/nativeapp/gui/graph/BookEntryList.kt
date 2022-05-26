package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand.EntryId
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun BookEntryList(bookEntryList: List<BookEntry>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = bookEntryList) { bookEntry ->
            BookEntryCard(bookEntry)
        }
    }
}

@Preview
@Composable
fun BookEntryListPreview() {
    val bookEntryList = listOf(
        BookEntry(id = 1, title = "Entry Hall", note = "Start of adventure"),
        BookEntry(id = 2, title = "Throne")
    )
    AdventureBookResolverTheme {
        BookEntryList(bookEntryList)
    }
}

@Composable
fun BookEntryCard(bookEntry: BookEntry) {
    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                EntryId(bookEntry)
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = bookEntry.title
                )
            }
            if (bookEntry.note.isNotEmpty()) {
                Text(bookEntry.note)
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
    AdventureBookResolverTheme {
        BookEntryCard(bookEntry)
    }
}