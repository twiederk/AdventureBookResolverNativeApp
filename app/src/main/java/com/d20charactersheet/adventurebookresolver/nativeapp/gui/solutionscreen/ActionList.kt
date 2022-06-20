package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme


@Composable
fun ActionList(actions: List<Action>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = actions) { action ->
            ActionCard(action)
        }
    }
}

@Preview
@Composable
fun ActionListPreview() {
    val actions = listOf(
        Action(
            label = "First Label",
            source = BookEntry(id = 182, title = "First Source Title"),
            destination = BookEntry(id = 242, wayMark = WayMark.NORMAL, visit = Visit.VISITED)
        ),
        Action(
            label = "Second Label",
            source = BookEntry(id = 169, title = "Second Source Title"),
            destination = BookEntry(id = 339, wayMark = WayMark.WAY_POINT, visit = Visit.UNVISITED)
        ),
        Action(
            label = "Third Label",
            source = BookEntry(id = 29, title = "Third Source Title"),
            destination = BookEntry(id = 20, wayMark = WayMark.DEAD_END, visit = Visit.UNVISITED)
        )
    )
    AdventureBookResolverTheme {
        ActionList(actions)
    }
}


@Composable
fun ActionCard(action: Action) {
    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SourceEntry(action)
            Action(action)
            DestinationEntry(action)
        }
    }
}

@Composable
private fun SourceEntry(action: Action) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        BookEntryId(action.source)
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = action.source.title
        )
    }
}

@Composable
private fun Action(action: Action) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(action.label)
    }
}

@Composable
private fun DestinationEntry(action: Action) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        BookEntryId(action.destination)
    }
}

@Composable
@Preview
fun ActionCardPreview() {
    val action = Action(
        label = "myLabel",
        source = BookEntry(id = 182, title = "mySourceTitle"),
        destination = BookEntry(id = 242, wayMark = WayMark.NORMAL, visit = Visit.UNVISITED)
    )
    AdventureBookResolverTheme {
        ActionCard(action)
    }
}


