package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun EntryId(entry: BookEntry) {
    val entryColor = ActionColorCompose().getColor(entry.wayMark, entry.visit)
    Box(
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .clip(CircleShape)
            .background(entryColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = entry.id.toString(),
        )
    }
}

@Preview(name = "Unvisited Entry", showBackground = true)
@Composable
fun BookEntryIdViewUnvisitedPreview() {
    AdventureBookResolverTheme {
        EntryId(BookEntry(id = 242, wayMark = WayMark.NORMAL, visit = Visit.UNVISITED))
    }
}

@Preview(name = "Visited Entry", showBackground = true)
@Composable
fun BookEntryIdViewVisitedPreview() {
    AdventureBookResolverTheme {
        EntryId(BookEntry(id = 242, wayMark = WayMark.NORMAL, visit = Visit.VISITED))
    }
}

@Preview(name = "Way Point Entry", showBackground = true)
@Composable
fun BookEntryIdViewWayPointPreview() {
    AdventureBookResolverTheme {
        EntryId(BookEntry(id = 242, wayMark = WayMark.WAY_POINT, visit = Visit.UNVISITED))
    }
}

@Preview(name = "Dead End Entry", showBackground = true)
@Composable
fun BookEntryIdViewDeadEndPreview() {
    AdventureBookResolverTheme {
        EntryId(BookEntry(id = 242, wayMark = WayMark.DEAD_END, visit = Visit.UNVISITED))
    }
}