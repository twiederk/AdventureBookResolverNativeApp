package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun CreateBookScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    onCreateClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Title") }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(onClick = { onCreateClick() }) {
                Text("Create")
            }
            Button(onClick = { onCancelClick() }) {
                Text("Cancel")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateBookScreenPreview() {
    AdventureBookResolverTheme {
        CreateBookScreen(
            title = "",
            onTitleChange = { },
            onCreateClick = { }
        ) {

        }
    }
}