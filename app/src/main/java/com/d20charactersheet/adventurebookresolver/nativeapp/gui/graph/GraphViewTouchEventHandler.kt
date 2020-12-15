package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.MotionEvent
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GraphViewTouchEventHandler : KoinComponent {

    private val game: Game by inject()
    private val bookRenderer: BookRenderer by inject()
    private val entryDialog: EntryDialog by inject()

    fun onTouchEvent(graphView: GraphView, event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                graphView.touchX = event.x - graphView.viewportX
                graphView.touchY = event.y - graphView.viewportY
                val bookEntry = bookRenderer.touch(graphView.touchX, graphView.touchY)
                bookEntry?.let {
                    if (game.isCurrentEntry(it)) {
                        entryDialog.show(graphView.context)
                    } else {
                        game.move(it.id)
                        graphView.center()
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                graphView.viewportX = event.x - graphView.touchX
                graphView.viewportY = event.y - graphView.touchY

            }

        }
        return true
    }

}