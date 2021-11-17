package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GraphCanvas(
    private val view: View,
    private val actionColor: ActionColor = ActionColor(),
    val graphPaint: GraphPaint = GraphPaint
) : KoinComponent {

    private val bookRenderer: TraversalBookRenderer by inject()

    var scale: Float = 1F
    private var graphEntries: List<GraphEntry> = emptyList()
    private var graphEdges: List<GraphEdge> = emptyList()

    fun translate(canvas: Canvas, viewportX: Float, viewportY: Float) {
        canvas.translate(viewportX, viewportY)
    }

    fun calculate() {
        val (entries, edges) = bookRenderer.render()
        if (scale != 1.0F) {
            graphEntries = scaleEntries(entries)
            graphEdges = scaleEdges(edges)
        } else {
            graphEntries = entries
            graphEdges = edges
        }
    }

    fun render(canvas: Canvas) {
        graphEntries.forEach { drawGraphEntry(it, canvas) }
        graphEdges.forEach { drawGraphEdge(it, canvas) }
    }

    private fun scaleEntries(entries: List<GraphEntry>): List<GraphEntry> {
        val scaledEntries = mutableListOf<GraphEntry>()
        entries.stream().forEach { graphEntry ->
            scaledEntries.add(
                graphEntry.copy(
                    left = graphEntry.left * scale,
                    top = graphEntry.top * scale,
                    right = graphEntry.right * scale,
                    bottom = graphEntry.bottom * scale,
                )
            )
        }
        return scaledEntries
    }


    private fun scaleEdges(edges: List<GraphEdge>): List<GraphEdge> {
        val scaledEdges = mutableListOf<GraphEdge>()
        edges.stream().forEach { graphEdge ->
            scaledEdges.add(
                graphEdge.copy(
                    startX = graphEdge.startX * scale,
                    startY = graphEdge.startY * scale,
                    endX = graphEdge.endX * scale,
                    endY = graphEdge.endY * scale,
                    labelX = graphEdge.labelX * scale,
                    labelY = graphEdge.labelY * scale
                )
            )
        }
        return scaledEdges
    }

    private fun drawGraphEntry(graphEntry: GraphEntry, canvas: Canvas) {
        drawCurrentEntryRect(graphEntry, canvas)
        drawEntry(graphEntry, canvas)
    }

    private fun drawCurrentEntryRect(graphEntry: GraphEntry, canvas: Canvas) {
        if (graphEntry.current) {
            canvas.drawRect(
                graphEntry.left - (50 * scale),
                graphEntry.top - (50 * scale),
                graphEntry.right + (50 * scale),
                graphEntry.bottom + (50 * scale),
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
        val scaledTextPaint = graphPaint.getScaledTextPaint(scale)
        canvas.drawText(
            "(${graphEntry.entry.id})",
            graphEntry.left,
            graphEntry.top + (100 * scale),
            scaledTextPaint
        )
        canvas.drawText(
            graphEntry.entry.title,
            graphEntry.left,
            graphEntry.bottom,
            scaledTextPaint
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
        canvas.drawCircle(graphEdge.endX, graphEdge.endY, 30f * scale, GraphPaint.edgePaint)

        canvas.drawText(
            graphEdge.label,
            graphEdge.labelX,
            graphEdge.labelY,
            graphPaint.getScaledTextPaint(scale)
        )
    }

    private fun setPaintColor(paint: Paint, entry: BookEntry) {
        paint.color = actionColor.getColor(view, entry.wayMark, entry.visit)
    }

    fun getCenterOfCurrentGraphEntry(): Pair<Float, Float> {
        val currentGraphEntry = graphEntries.find { graphEntry -> graphEntry.entry.id == bookRenderer.currentEntryId }
        if (currentGraphEntry != null) {
            val centerX = currentGraphEntry.left + (currentGraphEntry.width / 2)
            val centerY = currentGraphEntry.top + (currentGraphEntry.height / 2)
            return Pair(centerX, centerY)
        }
        return Pair(0F, 0F)
    }

    fun touch(x: Float, y: Float): BookEntry? {
        return graphEntries.find { it.left <= x && it.right >= x && it.top <= y && it.bottom >= y }?.entry
    }

}
