package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.R


@Composable
fun WayMarkDropDown(
    modifier: Modifier = Modifier,
    wayMark: WayMark,
    onWayMarkSelected: (WayMark) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(30.dp)
            .clickable { expanded = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = ContentAlpha.disabled
                ),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(16.dp)
                .weight(1f)
        ) {
            drawCircle(color = wayMark.color)
        }
        Text(
            modifier = Modifier.weight(8f),
            text = wayMark.name,
            style = MaterialTheme.typography.subtitle2
        )
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(angle)
                .weight(weight = 1.5f),
            onClick = { expanded = true }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = R.string.drop_down_arrow)
            )
        }
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.94f),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onWayMarkSelected(WayMark.NORMAL)
                }
            ) {
                WayMarkItem(wayMark = WayMark.NORMAL)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onWayMarkSelected(WayMark.WAY_POINT)
                }
            ) {
                WayMarkItem(wayMark = WayMark.WAY_POINT)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onWayMarkSelected(WayMark.DEAD_END)
                }
            ) {
                WayMarkItem(wayMark = WayMark.DEAD_END)
            }

        }
    }
}


@Preview
@Composable
private fun WayMarkDropDownPreview() {
    WayMarkDropDown(
        wayMark = WayMark.NORMAL,
        onWayMarkSelected = { }
    )
}