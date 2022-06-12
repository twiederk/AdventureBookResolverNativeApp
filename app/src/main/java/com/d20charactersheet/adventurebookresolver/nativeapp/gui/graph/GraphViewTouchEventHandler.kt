package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.MotionEvent
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreenViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GraphViewTouchEventHandler(private val onEntryTouch: () -> Unit) : KoinComponent {

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
                showEntryScreen(bookEntry)
            } else if (game.isEntryOfNextEntries(bookEntry)) {
                game.move(bookEntry.id)
                graphView.renderMode = RenderMode.CENTER
            }
        }
    }

    private fun showEntryScreen(bookEntry: BookEntry) {
        entryScreenViewModel.initBookEntry(bookEntry)
        onEntryTouch()
    }

    private fun getTouchedBookEntry(
        graphView: GraphView,
        event: MotionEvent,
        graphCanvas: GraphCanvas
    ): BookEntry? {
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