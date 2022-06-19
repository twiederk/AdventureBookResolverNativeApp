package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.MEDIUM_PADDING

@Composable
fun CreateBookScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    onCreateClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CreateBookScreenAppBar(
                onBackClick = onCancelClick
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(all = MEDIUM_PADDING)

            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text("Title") }
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onCreateClick() }) {
                    Text("Create")
                }
            }
        }
    )

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