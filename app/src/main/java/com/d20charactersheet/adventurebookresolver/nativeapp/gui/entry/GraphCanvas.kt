package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry

class GraphCanvas(private val view: View, private val actionColor: ActionColor = ActionColor()) {

    fun translate(canvas: Canvas, viewportX: Float, viewportY: Float) {
        canvas.translate(viewportX, viewportY)
    }

    fun render(canvas: Canvas, entries: List<GraphEntry>, edges: List<GraphEdge>) {
        entries.forEach { drawGraphEntry(it, canvas) }
        edges.forEach { drawGraphEdge(it, canvas) }
    }

    private fun drawGraphEntry(graphEntry: GraphEntry, canvas: Canvas) {
        drawCurrentEntryRect(graphEntry, canvas)
        drawEntry(graphEntry, canvas)
    }

    private fun drawCurrentEntryRect(graphEntry: GraphEntry, canvas: Canvas) {
        if (graphEntry.current) {
            canvas.drawRect(
                graphEntry.left - 50,
                graphEntry.top - 50,
                graphEntry.right + 50,
                graphEntry.bottom + 50,
                GraphPaint.currentEntryPaint
            )
        }
    }

    private fun drawEntry(graphEntry: GraphEntry, canvas: Canvas) {
        setPaintColor(GraphPaint.entryPaint, graphEntry.entry)
        canvas.drawRect(
            graphEntry.left,
            graphEntry.top,
            graphEntry.right,
            graphEntry.bottom,
            GraphPaint.entryPaint
        )
        canvas.drawText(
            "(${graphEntry.entry.id})",
            graphEntry.left,
            graphEntry.top + (100),
            GraphPaint.textPaint
        )
        canvas.drawText(
            graphEntry.entry.title,
            graphEntry.left,
            graphEntry.bottom,
            GraphPaint.textPaint
        )
    }


    private fun drawGraphEdge(graphEdge: GraphEdge, canvas: Canvas) {
        setPaintColor(GraphPaint.edgePaint, graphEdge.dest)
        canvas.drawLine(
            graphEdge.startX,
            graphEdge.startY,
            graphEdge.endX,
            graphEdge.endY,
            GraphPaint.edgePaint
        )
        canvas.drawCircle(graphEdge.endX, graphEdge.endY, 30f, GraphPaint.edgePaint)
    }

    private fun setPaintColor(paint: Paint, entry: BookEntry) {
        paint.color = actionColor.getColor(view, entry.wayMark, entry.visit)
    }


}
