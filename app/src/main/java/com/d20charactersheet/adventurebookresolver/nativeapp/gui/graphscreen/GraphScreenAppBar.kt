package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme


@Composable
fun GraphScreenAppBar(
    title: String,
    onNavigationIconClick: () -> Unit,
    onZoomChange: (Float) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer"
                )
            }
        },
        title = {
            Text(text = title)
        },
        actions = {
            ZoomDropDown(onZoomChange = onZoomChange)
        }
    )
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
            ZoomDropdownMenuItem(label = "10%", onClick = { expanded = false; onZoomChange(0.1F) })
            ZoomDropdownMenuItem(label = "25%", onClick = { expanded = false; onZoomChange(0.25F) })
            ZoomDropdownMenuItem(label = "50%", onClick = { expanded = false; onZoomChange(0.5F) })
            ZoomDropdownMenuItem(label = "100%", onClick = { expanded = false; onZoomChange(1.0F) })
            ZoomDropdownMenuItem(label = "200%", onClick = { expanded = false; onZoomChange(2.0F) })
        }
    }
}


@Composable
fun ZoomDropdownMenuItem(label: String, onClick: () -> Unit) {
    DropdownMenuItem(
        onClick = { onClick() }
    ) {
        Text(text = label)
    }
}


@Preview
@Composable
fun GraphScreenAppBarPreview() {
    AdventureBookResolverTheme {
        GraphScreenAppBar(
            title = "myTitle",
            onNavigationIconClick = { }
        ) { }
    }
}
