package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.MotionEvent
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GraphViewTouchEventHandler : KoinComponent {

    private val game: Game by inject()
    private val entryDialog: EntryDialog by inject()

    fun onTouchEvent(graphView: GraphView, graphCanvas: GraphCanvas, event: MotionEvent) {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                graphView.touchX = event.x - graphView.viewportX
                graphView.touchY = event.y - graphView.viewportY
                val bookEntry = graphCanvas.touch(graphView.touchX, graphView.touchY)
                bookEntry?.let {
                    if (game.isCurrentEntry(it)) {
                        entryDialog.show(graphView.context)
                    } else {
                        game.move(it.id)
                        graphView.renderMode = RenderMode.CENTER
                    }
                }
                return
            }

            MotionEvent.ACTION_MOVE -> {
                val bookEntry = graphCanvas.touch(graphView.touchX, graphView.touchY)
                if (bookEntry == null) {
                    graphView.renderMode = RenderMode.FREE
                    graphView.viewportX = event.x - graphView.touchX
                    graphView.viewportY = event.y - graphView.touchY
                }
                return
            }

        }
    }

}