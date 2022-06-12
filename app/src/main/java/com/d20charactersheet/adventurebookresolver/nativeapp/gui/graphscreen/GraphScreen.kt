package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphView
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphViewTouchEventHandler
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme

@Composable
fun GraphScreen(
    title: String,
    onZoomChange: (Float) -> Unit,
    onSaveClick: () -> Unit,
    onCreateClick: () -> Unit,
    onLoadClick: () -> Unit,
    onRestartClick: () -> Unit,
    onRenderClick: () -> Unit,
    onFabClick: () -> Unit,
    onEntryTouch: () -> Unit,
    scale: Float
) {
    Scaffold(
        topBar = {
            GraphAppBar(
                title = title,
                onZoomChange = onZoomChange,
                onSaveClick = onSaveClick,
                onCreateClick = onCreateClick,
                onLoadClick = onLoadClick,
                onRestartClick = onRestartClick,
                onRenderClick = onRenderClick
            )
        },
        content = {
            GraphViewContainer(
                scale = scale,
                onEntryTouch = onEntryTouch
            )
        },
        floatingActionButton = {
            AddActionFab(onFabClicked = onFabClick)
        }
    )

}


@Composable
fun GraphViewContainer(
    scale: Float,
    onEntryTouch: () -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val graphView = GraphView(context)
            graphView.setOnTouchListener(GraphViewTouchEventHandler(onEntryTouch = onEntryTouch))
            graphView
        },
        update = { graphView ->
            graphView.scale(scale)
        }
    )
}

@Composable
fun AddActionFab(
    onFabClicked: () -> Unit
) {
    FloatingActionButton(
        onClick = { onFabClicked() },
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Action"
        )
    }
}


@Preview
@Composable
fun GraphScreenCompose() {
    AdventureBookResolverTheme {
        GraphScreen(
            title = "myTitle",
            onZoomChange = { },
            onSaveClick = { },
            onCreateClick = { },
            onLoadClick = { },
            onRestartClick = { },
            onRenderClick = { },
            onFabClick = { },
            onEntryTouch = { },
            scale = 1.0F,
        )
    }
}
