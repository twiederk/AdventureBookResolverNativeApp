package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme


@Composable
fun GraphScreenAppBar(
    title: String,
    onZoomChange: (Float) -> Unit,
    onSaveClick: () -> Unit,
    onCreateClick: () -> Unit,
    onLoadClick: () -> Unit,
    onRestartClick: () -> Unit,
    onRenderClick: () -> Unit,
    onSolutionClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            SaveAction(onSaveClick = onSaveClick)
            ZoomDropDown(onZoomChange = onZoomChange)
            ActionsDropDown(
                onCreateClick = onCreateClick,
                onLoadClick = onLoadClick,
                onRestartClick = onRestartClick,
                onRenderClick = onRenderClick,
                onSolutionClick = onSolutionClick
            )
        }
    )
}


@Composable
fun SaveAction(
    onSaveClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.testTag("save_icon"),
        onClick = { onSaveClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_save),
            contentDescription = "Save"
        )
    }
}


@Composable
fun ActionsDropDown(
    onCreateClick: () -> Unit,
    onLoadClick: () -> Unit,
    onRestartClick: () -> Unit,
    onRenderClick: () -> Unit,
    onSolutionClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        modifier = Modifier.testTag("actions_drop_down_icon"),
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = "Actions Drop Down",
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ActionMenuItem(label = "Create", onClick = { expanded = false; onCreateClick() })
            ActionMenuItem(label = "Load", onClick = { expanded = false; onLoadClick() })
            ActionMenuItem(label = "Restart", onClick = { expanded = false; onRestartClick() })
            ActionMenuItem(label = "Solution", onClick = { expanded = false; onSolutionClick() })
            ActionMenuItem(label = "Render", onClick = { expanded = false; onRenderClick() })
        }
    }
}


@Composable
private fun ActionMenuItem(label: String, onClick: () -> Unit) {
    DropdownMenuItem(
        onClick = { onClick() }
    ) {
        Text(text = label)
    }
}


@Composable
fun ZoomDropDown(
    onZoomChange: (Float) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        modifier = Modifier.testTag("zoom_drop_down_icon"),
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = "Zoom Drop Down",
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ActionMenuItem(label = "10%", onClick = { expanded = false; onZoomChange(0.1F) })
            ActionMenuItem(label = "25%", onClick = { expanded = false; onZoomChange(0.25F) })
            ActionMenuItem(label = "50%", onClick = { expanded = false; onZoomChange(0.5F) })
            ActionMenuItem(label = "100%", onClick = { expanded = false; onZoomChange(1.0F) })
            ActionMenuItem(label = "200%", onClick = { expanded = false; onZoomChange(2.0F) })
        }
    }
}


@Preview
@Composable
fun GraphScreenAppBarPreview() {
    AdventureBookResolverTheme {
        GraphScreenAppBar(
            title = "myTitle",
            onZoomChange = { },
            onSaveClick = { },
            onCreateClick = { },
            onLoadClick = { },
            onRestartClick = { },
            onRenderClick = { },
            onSolutionClick = { }
        )
    }
}
