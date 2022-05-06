package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun GenericCommandScreen(
    command: Command,
    argument: String,
    onCommandChange: (Command) -> Unit,
    onArgumentChange: (String) -> Unit,
    onExecuteClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Column {
        CommandDropDropDown(
            command = command,
            commandList = Command.sortedValues(),
            onCommandChange = onCommandChange
        )
        ArgumentTextField(argument, onArgumentChange)
        ExecuteButton(onExecuteClick)
        ClearButton(onClearClick)
    }
}

@Composable
fun CommandDropDropDown(
    command: Command,
    commandList: List<Command>,
    onCommandChange: (Command) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = command.value,
                onValueChange = { },
                label = { Text("Command") },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                commandList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onCommandChange(it)
                        }
                    ) {
                        Text(it.value)
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = true }
                )
        )
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
private fun ExecuteButton(onExecuteClick: () -> Unit) {
    Button(onClick = { onExecuteClick() }) {
        Text("Execute")
    }
}

@Composable
private fun ClearButton(onClearClick: () -> Unit) {
    Button(onClick = { onClearClick() }) {
        Text("Clear")
    }
}

@Preview
@Composable
fun GenericCommandScreenPreview() {
    AdventureBookResolverTheme {
        GenericCommandScreen(
            command = Command.Create,
            argument = "",
            onCommandChange = { },
            onArgumentChange = { },
            onExecuteClick = { },
            onClearClick = { }
        )
    }
}