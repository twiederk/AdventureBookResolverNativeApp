package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.graphics.Canvas
import android.view.View
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

class GraphCanvasTest {

    @Test
    fun translate() {
        // Arrange
        val canvas: Canvas = mock()

        // Act
        GraphCanvas(mock()).translate(canvas, 100f, 200f)

        // Assert
        verify(canvas).translate(100f, 200f)
    }

    @Test
    fun render_drawGraphEdge() {
        // Arrange
        val edges = listOf(
            GraphEdge(100f, 200f, 300f, 400f, "myLabel", 500f, 600f, BookEntry(1))
        )
        val actionColor: ActionColor = mock()
        val view: View = mock()
        val canvas: Canvas = mock()

        // Act
        GraphCanvas(view, actionColor).render(canvas, listOf(), edges)

        // Assert
        verify(actionColor).getColor(view, WayMark.NORMAL, Visit.UNVISITED)
        verify(canvas).drawLine(100f, 200f, 300f, 400f, GraphPaint.edgePaint)
        verify(canvas).drawCircle(300f, 400f, 30f, GraphPaint.edgePaint)
        verify(canvas).drawText("myLabel", 500f, 600f, GraphPaint.textPaint)
        verifyNoMoreInteractions(actionColor, canvas)
    }

    @Test
    fun render_drawGraphEntryWithNormalEntry() {
        // Arrange
        val entries = listOf(
            GraphEntry(100f, 200f, 300f, 400f, BookEntry(1, "myTitle"))
        )
        val actionColor: ActionColor = mock()
        val view: View = mock()
        val canvas: Canvas = mock()

        // Act
        GraphCanvas(view, actionColor).render(canvas, entries, listOf())

        // Assert
        verify(actionColor).getColor(view, WayMark.NORMAL, Visit.UNVISITED)
        verify(canvas).drawRect(100f, 200f, 300f, 400f, GraphPaint.entryPaint)
        verify(canvas).drawText("(1)", 100f, 300f, GraphPaint.textPaint)
        verify(canvas).drawText("myTitle", 100f, 400f, GraphPaint.textPaint)
        verifyNoMoreInteractions(actionColor, canvas)
    }

    @Test
    fun render_drawGraphEntryWithCurrentEntry() {
        // Arrange
        val entries = listOf(
            GraphEntry(100f, 200f, 300f, 400f, BookEntry(1, "myTitle"), true)
        )
        val actionColor: ActionColor = mock()
        val view: View = mock()
        val canvas: Canvas = mock()

        // Act
        GraphCanvas(view, actionColor).render(canvas, entries, listOf())

        // Assert
        verify(actionColor).getColor(view, WayMark.NORMAL, Visit.UNVISITED)
        verify(canvas).drawRect(50f, 150f, 350f, 450f, GraphPaint.currentEntryPaint)
        verify(canvas).drawRect(100f, 200f, 300f, 400f, GraphPaint.entryPaint)
        verify(canvas).drawText("(1)", 100f, 300f, GraphPaint.textPaint)
        verify(canvas).drawText("myTitle", 100f, 400f, GraphPaint.textPaint)
        verifyNoMoreInteractions(actionColor, canvas)
    }

}