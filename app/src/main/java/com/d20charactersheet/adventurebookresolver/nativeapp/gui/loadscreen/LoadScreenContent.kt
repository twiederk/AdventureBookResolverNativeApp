package com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun LoadScreenContent(
    fileNames: List<String>,
    onLoadClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = fileNames) { fileName ->
            FileCard(onLoadClick, fileName, onDeleteClick)
        }
    }
}

@Composable
private fun FileCard(onLoadClick: (String) -> Unit, fileName: String, onDeleteClick: (String) -> Unit) {
    Surface(
        modifier = Modifier.clickable { onLoadClick(fileName) },
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FileNameText(fileName)
            FileDeleteButton(onDeleteClick, fileName)
        }
    }
}

@Composable
private fun FileNameText(fileName: String) {
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = fileName
    )
}

@Composable
private fun FileDeleteButton(onDeleteClick: (String) -> Unit, fileName: String) {
    IconButton(onClick = { onDeleteClick(fileName) }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "delete",
            tint = Color.Gray
        )
    }
}

@Preview
@Composable
fun LoadScreenContentPreview() {
    AdventureBookResolverTheme {
        LoadScreenContent(
            fileNames = listOf("file 1", "file 2"),
            onLoadClick = { },
            onDeleteClick = { }
        )
    }
}
