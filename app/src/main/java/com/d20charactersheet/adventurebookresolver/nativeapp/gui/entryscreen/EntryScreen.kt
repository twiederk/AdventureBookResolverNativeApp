package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.EntryId
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.MEDIUM_PADDING

@Composable
fun EntryScreen(
    id: Int,
    title: String,
    note: String,
    visit: Visit,
    wayMark: WayMark,

    actions: List<Action>,
    onTitleChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit,
    onWayMarkSelected: (WayMark) -> Unit,
    onActionMoveClicked: (Int) -> Unit,
    onActionDeleteClicked: (Int) -> Unit,
    onBackNavigationClicked: () -> Unit
) {

    Scaffold(
        topBar = {
            EntryScreenAppBar(
                onBackClick = { onBackNavigationClicked() }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(all = MEDIUM_PADDING)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    EntryId(entry = BookEntry(id = id, visit = visit, wayMark = wayMark))
                    EntryTitle(title, onTitleChanged, onBackNavigationClicked)
                }
                EntryWayMark(wayMark, onWayMarkSelected)
                EntryActions(actions, onActionMoveClicked, onActionDeleteClicked)
                EntryNote(note, onNoteChanged)
            }
        }
    )


}


@Composable
private fun EntryTitle(
    title: String,
    onTitleChanged: (String) -> Unit,
    onBackNavigationClicked: () -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(title)) }
    if (textFieldValue.text != title) {
        textFieldValue = textFieldValue.copy(title)
    }
    OutlinedTextField(
        value = textFieldValue,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = MEDIUM_PADDING, bottom = MEDIUM_PADDING)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    textFieldValue =
                        textFieldValue.copy(selection = TextRange(0, textFieldValue.text.length))
                }
            },
        onValueChange = { text ->
            onTitleChanged(text.text)
            textFieldValue = text
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onBackNavigationClicked()
            }
        ),
        label = { Text(text = stringResource(id = R.string.entry_title)) },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true
    )
}


@Composable
private fun EntryWayMark(
    wayMark: WayMark,
    onWayMarkSelected: (WayMark) -> Unit
) {
    WayMarkDropDown(
        wayMark = wayMark,
        onWayMarkSelected = onWayMarkSelected
    )
}


@Composable
private fun EntryNote(note: String, onNoteChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxSize(),
        value = note,
        onValueChange = { onNoteChanged(it) },
        label = { Text(text = stringResource(id = R.string.entry_note)) },
        textStyle = MaterialTheme.typography.body1
    )
}


@Composable
fun EntryActions(
    actions: List<Action>,
    onActionMoveClicked: (Int) -> Unit,
    onActionDeleteClicked: (Int) -> Unit
) {
    ActionMoveList(
        actions = actions,
        onActionMoveClicked = onActionMoveClicked,
        onActionDeleteClicked = onActionDeleteClicked,
    )
}


@Preview(showBackground = true)
@Composable
fun EntryScreenPreview() {
    val bookEntry = BookEntry(
        id = 123,
        title = "Entry hall",
        note = "Start of adventure",
        visit = Visit.VISITED,
        wayMark = WayMark.WAY_POINT
    )
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
        EntryScreen(
            id = bookEntry.id,
            title = bookEntry.title,
            note = bookEntry.note,
            visit = bookEntry.visit,
            wayMark = bookEntry.wayMark,
            actions = actions,

            onTitleChanged = { },
            onNoteChanged = { },
            onWayMarkSelected = { },
            onActionMoveClicked = { },
            onActionDeleteClicked = { },
            onBackNavigationClicked = { }
        )
    }
}