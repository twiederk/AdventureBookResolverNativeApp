package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.MotionEvent
import android.view.View
import androidx.compose.ui.platform.ComposeView
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GraphViewTouchEventHandler : KoinComponent {

    private val game: Game by inject()
    private val entryScreenViewModel: EntryScreenViewModel by inject()

    fun onTouchEvent(graphView: GraphView, graphCanvas: GraphCanvas, event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> actionDown(graphView, graphCanvas, event)
            MotionEvent.ACTION_MOVE -> actionMove(graphView, graphCanvas, event)
        }
    }

    private fun actionDown(graphView: GraphView, graphCanvas: GraphCanvas, event: MotionEvent) {
        val bookEntry = getTouchedBookEntry(graphView, event, graphCanvas)
        if (bookEntry != null) {
            if (game.isCurrentEntry(bookEntry)) {
                showEntryScreen(graphView, bookEntry)
            } else if (game.isEntryOfNextEntries(bookEntry)) {
                game.move(bookEntry.id)
                graphView.renderMode = RenderMode.CENTER
            }
        }
    }

    private fun showEntryScreen(graphView: GraphView, bookEntry: BookEntry) {
        val frameLayout = graphView.parent.parent as View
        val composeView = frameLayout.findViewById<ComposeView>(R.id.entry_screen)
        entryScreenViewModel.initBookEntry(bookEntry)
        composeView.setContent {
            AdventureBookResolverTheme {
                EntryScreen(
                    id = entryScreenViewModel.id,
                    title = entryScreenViewModel.title,
                    note = entryScreenViewModel.note,
                    visit = entryScreenViewModel.visit,
                    wayMark = entryScreenViewModel.wayMark,
                    actions = entryScreenViewModel.actions,
                    onTitleChanged = { entryScreenViewModel.onTitleChanged(it) },
                    onNoteChanged = { entryScreenViewModel.onNoteChanged(it) },
                    onWayMarkSelected = { entryScreenViewModel.onWayMarkSelected(it) },
                    onActionMoveClicked = {
                        entryScreenViewModel.onActionMoveClicked(it)
                        graphView.invalidate()
                        composeView.visibility = View.INVISIBLE
                    },
                    onActionDeleteClicked = { entryScreenViewModel.onActionDeleteClicked(it) },
                    onBackNavigationClicked = {
                        graphView.invalidate()
                        composeView.visibility = View.INVISIBLE
                    }
                )
            }
        }
        composeView.visibility = View.VISIBLE
    }

    private fun getTouchedBookEntry(graphView: GraphView, event: MotionEvent, graphCanvas: GraphCanvas): BookEntry? {
        graphView.touchX = event.x - graphView.viewportX
        graphView.touchY = event.y - graphView.viewportY
        return graphCanvas.touch(graphView.touchX, graphView.touchY)
    }

    private fun actionMove(graphView: GraphView, graphCanvas: GraphCanvas, event: MotionEvent) {
        val bookEntry = graphCanvas.touch(graphView.touchX, graphView.touchY)
        if (bookEntry == null) {
            graphView.renderMode = RenderMode.FREE
            graphView.viewportX = event.x - graphView.touchX
            graphView.viewportY = event.y - graphView.touchY
        }

    }

}