package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun CreateActionScreen(
    actionLabel: String,
    entryId: String,
    errorMessage: String,
    onActionLabelChange: (String) -> Unit,
    onEntryIdChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onCreateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = actionLabel,
                modifier = Modifier.weight(4F),
                onValueChange = { onActionLabelChange(it) },
                label = { Text("Action") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)

            )
            OutlinedTextField(
                value = entryId,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.dp),
                onValueChange = { onEntryIdChange(it) },
                label = { Text("Id") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onCreateClick() }),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(onClick = { onCancelClick() }) {
                Text("Cancel")
            }
            Button(onClick = { onCreateClick() }) {
                Text("Create")
            }
        }
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateActionScreenPreview() {
    AdventureBookResolverTheme {
        CreateActionScreen(
            actionLabel = "myAction",
            entryId = "10",
            errorMessage = "myErrorMessage",
            onActionLabelChange = { },
            onEntryIdChange = { },
            onCancelClick = { },
            onCreateClick = { }
        )
    }
}
