package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun GenericCommandScreen(
    argument: String,
    onArgumentChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onPathClick: () -> Unit,
    onWayPointClick: () -> Unit,
    onUnvisitedClick: () -> Unit,
    onSolveClick: () -> Unit,
    onRollDieClick: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArgumentTextField(argument, onArgumentChange)
            SearchButton(onSearchClick)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PathButton(onPathClick)
            WayPointButton(onWayPointClick)
            UnvisitedButton(onUnvisitedClick)
            SolveButton(onSolveClick)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RollDieButton("1d6", onRollDieClick)
            RollDieButton("2d6", onRollDieClick)
        }
    }
}

@Composable
fun RollDieButton(dieRoll: String, onRollDie: (String) -> Unit) {
    Button(onClick = { onRollDie(dieRoll) }) {
        Text("Roll $dieRoll")
    }
}

@Composable
fun PathButton(onPathClick: () -> Unit) {
    Button(
        onClick = { onPathClick() }
    ) {
        Text("Path")
    }
}

@Composable
fun WayPointButton(onWaypointClick: () -> Unit) {
    Button(
        onClick = { onWaypointClick() }
    ) {
        Text("Way Points")
    }
}

@Composable
fun UnvisitedButton(onUnvisitedClick: () -> Unit) {
    Button(
        onClick = { onUnvisitedClick() }
    ) {
        Text("Unvisited")
    }
}

@Composable
fun SolveButton(onSolveClick: () -> Unit) {
    Button(
        onClick = { onSolveClick() }
    ) {
        Text("Solve")
    }
}


@Composable
private fun ArgumentTextField(argument: String, onArgumentChange: (String) -> Unit) {
    OutlinedTextField(
        value = argument,
        onValueChange = onArgumentChange,
        label = { Text("Argument") }
    )
}

@Composable
private fun SearchButton(onSearchClick: () -> Unit) {
    Button(
        onClick = { onSearchClick() }
    ) {
        Text("Search")
    }
}


@Preview
@Composable
fun GenericCommandScreenPreview() {
    AdventureBookResolverTheme {
        GenericCommandScreen(
            argument = "",
            onArgumentChange = { },
            onSearchClick = { },
            onPathClick = { },
            onWayPointClick = { },
            onUnvisitedClick = { },
            onSolveClick = { },
            onRollDieClick = { }
        )
    }
}
