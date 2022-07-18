package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme


@Composable
fun GraphScreenDrawer(
    onCreateClick: () -> Unit,
    onLoadClick: () -> Unit,
    onRestartClick: () -> Unit,
    onRenderClick: () -> Unit,
    onImportClick: () -> Unit,
    onExportClick: () -> Unit,
    onItemClick: () -> Unit
) {
    DrawerHeader()
    DrawerBody(
        onCreateClick = onCreateClick,
        onLoadClick = onLoadClick,
        onRestartClick = onRestartClick,
        onRenderClick = onRenderClick,
        onImportClick = onImportClick,
        onExportClick = onExportClick,
        onItemClick = onItemClick
    )

}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Actions")
    }
}


@Preview
@Composable
fun DrawerHeaderPreview() {
    AdventureBookResolverTheme {
        DrawerHeader()
    }
}


@Composable
fun DrawerBody(
    onCreateClick: () -> Unit,
    onLoadClick: () -> Unit,
    onRestartClick: () -> Unit,
    onRenderClick: () -> Unit,
    onImportClick: () -> Unit,
    onExportClick: () -> Unit,
    onItemClick: () -> Unit
) {
    DrawerMenuItem(label = "Create", onClick = { onCreateClick(); onItemClick() })
    DrawerMenuItem(label = "Load", onClick = { onLoadClick(); onItemClick() })
    DrawerMenuItem(label = "Restart", onClick = { onRestartClick(); onItemClick() })
    DrawerMenuItem(label = "Render", onClick = { onRenderClick(); onItemClick() })
    Divider(
        modifier = Modifier.fillMaxWidth(),
        startIndent = 10.dp,
        thickness = 1.dp
    )
    DrawerMenuItem(label = "Import", onClick = { onImportClick(); onItemClick() })
    DrawerMenuItem(label = "Export", onClick = { onExportClick(); onItemClick() })
}


@Composable
fun DrawerMenuItem(label: String, onClick: () -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        text = label
    )
}


@Preview
@Composable
fun GraphScreenDrawerPreview() {
    AdventureBookResolverTheme {
        GraphScreenDrawer(
            onCreateClick = { },
            onLoadClick = { },
            onRestartClick = { },
            onRenderClick = { },
            onImportClick = { },
            onExportClick = { },
            onItemClick = { },
        )
    }
}

