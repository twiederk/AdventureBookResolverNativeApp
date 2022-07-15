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
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphView
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphViewTouchEventHandler
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation.BottomBar
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.navigation.ScreenRoute
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import kotlinx.coroutines.launch

@Composable
fun GraphScreen(
    bookViewModel: BookViewModel,
    graphViewModel: GraphViewModel,
    onLoadClick: () -> Unit,
    onRestartClick: () -> Unit,
    onRenderClick: () -> Unit,
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            GraphScreenAppBar(
                title = bookViewModel.title,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            ) { graphViewModel.onScaleChange(it) }
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            GraphScreenDrawer(
                onCreateClick = { navController.navigate(ScreenRoute.CreateBookScreenRoute.route) },
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
                scale = graphViewModel.scale,
                onEntryTouch = { navController.navigate(ScreenRoute.EntryScreen.route) }
            )
        },
        floatingActionButton = {
            AddActionFab(onFabClicked = { navController.navigate(ScreenRoute.CreateActionScreen.route) })
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

    val game = Game()
    val bookViewModel = BookViewModel(game)
    bookViewModel.onTitleChange("myTitle")

    val graphViewModel = GraphViewModel()
    graphViewModel.onScaleChange(1.0F)

    AdventureBookResolverTheme {
        GraphScreen(
            bookViewModel = bookViewModel,
            graphViewModel = graphViewModel,
            onLoadClick = { },
            onRestartClick = { },
            onRenderClick = { },
            navController = rememberNavController(),
        )
    }
}
