package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark

@Composable
fun WayMarkItem(wayMark: WayMark) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(16.dp)) {
            drawCircle(color = wayMark.color)
        }
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = wayMark.name,
            color = MaterialTheme.colors.onSurface
        )
    }
}

val WayMark.color: Color
    get() = when (this) {
        WayMark.NORMAL -> Color.Gray
        WayMark.WAY_POINT -> Color.Green
        WayMark.DEAD_END -> Color.Red
    }


@Preview
@Composable
fun WayMarkItemPreview() {
    WayMarkItem(wayMark = WayMark.WAY_POINT)
}