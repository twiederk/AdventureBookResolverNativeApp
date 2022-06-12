package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand.ActionColorCompose
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun ActionMoveList(
    actions: List<Action>,
    onActionMoveClicked: (Int) -> Unit,
    onActionDeleteClicked: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = actions) { action ->
            ActionMoveCard(
                action = action,
                onActionMoveClicked = onActionMoveClicked,
                onActionDeleteClicked = onActionDeleteClicked
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun MoveActionListPreview() {
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
        ActionMoveList(
            actions = actions,
            onActionMoveClicked = { },
            onActionDeleteClicked = { }
        )
    }
}


@Composable
fun ActionMoveCard(
    action: Action,
    onActionMoveClicked: (Int) -> Unit,
    onActionDeleteClicked: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionLabel(action)
        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            ActionDeleteIcon(action, onActionDeleteClicked)
            ActionMoveButton(action, onActionMoveClicked)
        }
    }
}


@Composable
private fun ActionLabel(action: Action) {
    Text(action.label)
}


@Composable
private fun ActionDeleteIcon(action: Action, onActionDeleteClicked: (Int) -> Unit) {
    IconButton(onClick = { onActionDeleteClicked(action.destination.id) }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.entry_action_delete),
            tint = Color.Gray
        )
    }
}


@Composable
private fun ActionMoveButton(action: Action, onActionMoveClicked: (Int) -> Unit) {
    val entryColor = ActionColorCompose().getColor(action.destination.wayMark, action.destination.visit)
    Button(
        onClick = { onActionMoveClicked(action.destination.id) },
        colors = ButtonDefaults.buttonColors(backgroundColor = entryColor)
    ) {
        Text(
            text = action.destination.id.toString()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ActionMoveCardPreview() {
    val action = Action(
        label = "myLabel",
        source = BookEntry(id = 182),
        destination = BookEntry(id = 242, wayMark = WayMark.NORMAL, visit = Visit.UNVISITED)
    )
    AdventureBookResolverTheme {
        ActionMoveCard(
            action,
            onActionMoveClicked = { },
            onActionDeleteClicked = { }
        )
    }
}
