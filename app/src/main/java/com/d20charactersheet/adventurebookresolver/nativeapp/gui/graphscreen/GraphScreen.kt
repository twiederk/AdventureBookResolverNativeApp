package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphView
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphViewTouchEventHandler
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation.BottomBar
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import kotlinx.coroutines.launch

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
    scale: Float,
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            GraphScreenAppBar(
                title = title,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                onZoomChange = onZoomChange,
                onSaveClick = onSaveClick
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            GraphScreenDrawer(
                onCreateClick = onCreateClick,
                onLoadClick = onLoadClick,
                onRestartClick = onRestartClick,
                onRenderClick = onRenderClick
            ) {
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        },
        content = {
            GraphViewContainer(
                scale = scale,
                onEntryTouch = onEntryTouch
            )
        },
        floatingActionButton = {
            AddActionFab(onFabClicked = onFabClick)
        },
        bottomBar = {
            BottomBar(navController = navController)
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
            navController = rememberNavController(),
        )
    }
}
