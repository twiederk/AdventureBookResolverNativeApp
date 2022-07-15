package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.BookEntryId
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.MEDIUM_PADDING

@Composable
fun EntryScreen(
    entryScreenViewModel: EntryScreenViewModel,
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            EntryScreenAppBar(
                onBackClick = { navController.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(all = MEDIUM_PADDING)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BookEntryId(
                    entry = BookEntry(
                        id = entryScreenViewModel.id,
                        visit = entryScreenViewModel.visit,
                        wayMark = entryScreenViewModel.wayMark
                    )
                )
                EntryTitle(
                    title = entryScreenViewModel.title,
                    onTitleChanged = { entryScreenViewModel.onTitleChanged(it) },
                    onBackNavigationClicked = { navController.popBackStack() }
                )
            }
            EntryWayMark(
                wayMark = entryScreenViewModel.wayMark,
                onWayMarkSelected = { entryScreenViewModel.onWayMarkSelected(it) }
            )
            EntryActions(
                actions = entryScreenViewModel.actions,
                onActionDeleteClicked = { entryScreenViewModel.onActionDeleteClicked(it) },
                onActionMoveClicked = {
                    entryScreenViewModel.onActionMoveClicked(it)
                    navController.popBackStack()
                }
            )
            EntryRunToThisButton(onRunClick = { entryScreenViewModel.onRunClick() })
            EntryNote(
                note = entryScreenViewModel.note,
                onNoteChanged = { entryScreenViewModel.onNoteChanged(it) },
                onBackNavigationClicked = { navController.popBackStack() }
            )
        }
    }


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
            .onFocusEvent {
                if (textFieldValue.text == "Untitled") {
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
private fun EntryNote(
    note: String,
    onNoteChanged: (String) -> Unit,
    onBackNavigationClicked: () -> Unit

) {
    OutlinedTextField(
        modifier = Modifier.fillMaxSize(),
        value = note,
        onValueChange = { onNoteChanged(it) },
        label = { Text(text = stringResource(id = R.string.entry_note)) },
        textStyle = MaterialTheme.typography.body1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onBackNavigationClicked()
            }
        )
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


@Composable
fun EntryRunToThisButton(
    onRunClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onRunClick() }
    ) {
        Text("Run To This Entry")
    }
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
    val entryScreenViewModel = EntryScreenViewModel(Game())
    entryScreenViewModel.initBookEntry(bookEntry)
    entryScreenViewModel.actions = actions

    AdventureBookResolverTheme {
        EntryScreen(
            entryScreenViewModel = entryScreenViewModel,
            navController = rememberNavController()
        )
    }
}