package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.*

class GraphCanvasKoinTest : KoinTest {

    private val bookRenderer: TraversalBookRenderer by inject()

    private var scaledTextPaint: Paint = mock()
    private var scaledEdgePaint: Paint = mock()
    private var actionColor: ActionColor = mock()
    private var graphPaint: GraphPaint = mock()


    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<TraversalBookRenderer>()

        whenever(graphPaint.getScaledTextPaint(any())).thenReturn(scaledTextPaint)
        whenever(graphPaint.getScaledEdgePaint(any())).thenReturn(scaledEdgePaint)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun translate() {
        // arrange
        val canvas: Canvas = mock()

        // act
        GraphCanvas(mock()).translate(canvas, 100f, 200f)

        // assert
        verify(canvas).translate(100f, 200f)
    }

    @Test
    fun render_drawGraphEdge() {
        // arrange
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(),
            listOf(GraphEdge(100f, 200f, 300f, 400f, "myLabel", 500f, 600f, BookEntry(1)))
        )
        val view: View = mock()
        val canvas: Canvas = mock()
        val underTest = GraphCanvas(view, actionColor, graphPaint)
        underTest.calculate()

        // act
        underTest.render(canvas)

        // assert
        val scale = underTest.scale
        verify(actionColor).getColor(view, WayMark.NORMAL, Visit.UNVISITED)
        verify(graphPaint).getScaledTextPaint(scale)
        verify(graphPaint).getScaledEdgePaint(scale)
        verify(canvas).drawLine(100f * scale, 200f * scale, 300f * scale, 400f * scale, scaledEdgePaint)
        verify(canvas).drawCircle(300f * scale, 400f * scale, 30f * scale, GraphPaint.edgePaint)
        verify(canvas).drawText("myLabel", 500f * scale, 600f * scale, scaledTextPaint)
        verifyNoMoreInteractions(canvas, actionColor, graphPaint)
    }

    @Test
    fun render_withScale2_drawScaledGraphEdge() {
        // arrange
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(),
            listOf(GraphEdge(100f, 200f, 300f, 400f, "myLabel", 500f, 600f, BookEntry(1)))
        )
        val view: View = mock()
        val canvas: Canvas = mock()
        val underTest = GraphCanvas(view, actionColor, graphPaint)
        underTest.scale = 2F
        underTest.calculate()

        // act
        underTest.render(canvas)

        // assert
        val scale = underTest.scale
        verify(actionColor).getColor(view, WayMark.NORMAL, Visit.UNVISITED)
        verify(graphPaint).getScaledTextPaint(scale)
        verify(graphPaint).getScaledEdgePaint(scale)
        verify(canvas).drawLine(100f * scale, 200f * scale, 300f * scale, 400f * scale, scaledEdgePaint)
        verify(canvas).drawCircle(300f * scale, 400f * scale, 30f * scale, GraphPaint.edgePaint)
        verify(canvas).drawText("myLabel", 500f * scale, 600f * scale, scaledTextPaint)
        verifyNoMoreInteractions(canvas, actionColor, graphPaint)
    }

    @Test
    fun render_drawGraphEntryWithNormalEntry() {
        // arrange
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(GraphEntry(entry = BookEntry(1, "myTitle"), left = 100f, top = 200f, right = 300f, bottom = 400f)),
            listOf()
        )
        val view: View = mock()
        val canvas: Canvas = mock()
        val underTest = GraphCanvas(view, actionColor, graphPaint)
        underTest.calculate()

        // act
        underTest.render(canvas)

        // assert
        val scale = underTest.scale
        verify(actionColor).getColor(view, WayMark.NORMAL, Visit.UNVISITED)
        verify(graphPaint).getScaledTextPaint(scale)
        verify(canvas).drawRect(100f * scale, 200f * scale, 300f * scale, 400f * scale, GraphPaint.entryPaint)
        verify(canvas).drawText("(1)", 100f * scale, 300f * scale, scaledTextPaint)
        verify(canvas).drawText("myTitle", 100f * scale, 400f * scale, scaledTextPaint)
        verifyNoMoreInteractions(canvas, actionColor, graphPaint)
    }

    @Test
    fun render_withScale2_drawGraphEntryWithNormalEntry() {
        // arrange
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(GraphEntry(entry = BookEntry(1, "myTitle"), left = 100f, top = 200f, right = 300f, bottom = 400f)),
            listOf()
        )

        val view: View = mock()
        val canvas: Canvas = mock()
        val underTest = GraphCanvas(view, actionColor, graphPaint)
        underTest.scale = 2F
        underTest.calculate()

        // act
        underTest.render(canvas)

        // assert
        val scale = underTest.scale
        verify(actionColor).getColor(view, WayMark.NORMAL, Visit.UNVISITED)
        verify(graphPaint).getScaledTextPaint(scale)
        verify(canvas).drawRect(100f * scale, 200f * scale, 300f * scale, 400f * scale, GraphPaint.entryPaint)
        verify(canvas).drawText("(1)", 100f * scale, 300f * scale, scaledTextPaint)
        verify(canvas).drawText("myTitle", 100f * scale, 400f * scale, scaledTextPaint)
        verifyNoMoreInteractions(canvas, actionColor, graphPaint)
    }

    @Test
    fun render_drawGraphEntryWithCurrentEntry() {
        // arrange
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(GraphEntry(entry = BookEntry(1, "myTitle"), left = 100f, top = 200f, right = 300f, bottom = 400f, current = true)),
            listOf()
        )
        val view: View = mock()
        val canvas: Canvas = mock()
        val underTest = GraphCanvas(view, actionColor, graphPaint)
        underTest.calculate()

        // act
        underTest.render(canvas)

        // assert
        val scale = underTest.scale
        verify(actionColor).getColor(view, WayMark.NORMAL, Visit.UNVISITED)
        verify(graphPaint).getScaledTextPaint(scale)
        verify(canvas).drawRect(50f * scale, 150f * scale, 350f * scale, 450f * scale, GraphPaint.currentEntryPaint)
        verify(canvas).drawRect(100f * scale, 200f * scale, 300f * scale, 400f * scale, GraphPaint.entryPaint)
        verify(canvas).drawText("(1)", 100f * scale, 300f * scale, scaledTextPaint)
        verify(canvas).drawText("myTitle", 100f * scale, 400f * scale, scaledTextPaint)
        verifyNoMoreInteractions(canvas, actionColor, graphPaint)
    }

    @Test
    fun render_drawGraphEntryWithCurrentEntry_withScale2() {
        // arrange
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(GraphEntry(entry = BookEntry(1, "myTitle"), left = 100f, top = 200f, right = 300f, bottom = 400f, current = true)),
            listOf()
        )
        val view: View = mock()
        val canvas: Canvas = mock()
        val underTest = GraphCanvas(view, actionColor, graphPaint)
        underTest.scale = 2F
        underTest.calculate()

        // act
        underTest.render(canvas)

        // assert
        val scale = underTest.scale
        verify(actionColor).getColor(view, WayMark.NORMAL, Visit.UNVISITED)
        verify(graphPaint).getScaledTextPaint(scale)
        verify(canvas).drawRect(50f * scale, 150f * scale, 350f * scale, 450f * scale, GraphPaint.currentEntryPaint)
        verify(canvas).drawRect(100f * scale, 200f * scale, 300f * scale, 400f * scale, GraphPaint.entryPaint)
        verify(canvas).drawText("(1)", 100f * scale, 300f * scale, scaledTextPaint)
        verify(canvas).drawText("myTitle", 100f * scale, 400f * scale, scaledTextPaint)
        verifyNoMoreInteractions(canvas, actionColor, graphPaint)
    }

    /**
     *  (1 - Entry Hall)
     */
    @Test
    fun centerOfCurrentBookEntry_singleBookEntry_centerOnSingleEntry() {
        // arrange
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(GraphEntry(entry = BookEntry(1), left = 0F, top = 0F, right = 100F, bottom = 250F, current = true)),
            listOf()
        )
        whenever(bookRenderer.currentEntryId) doReturn 1
        val underTest = GraphCanvas(mock(), mock())
        underTest.calculate()

        // act
        val (centerX, centerY) = underTest.getCenterOfCurrentGraphEntry()

        // assert
        assertThat(centerX).isEqualTo(50F)
        assertThat(centerY).isEqualTo(125F)
    }

    /**
     *                  (1 - Entry Hall)
     *        to throne |    | to library  | to kitchen
     *         ----------    |             |
     *         |             |             |
     *   (2 - Untitled)  (3 -Untitled)  (4 - Untitled)
     */
    @Test
    fun center_centerOnCurrentEntry_coordinatesOfCenter() {
        // arrange
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(
                GraphEntry(entry = BookEntry(2), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false),
                GraphEntry(entry = BookEntry(3), left = 180F, top = 500F, right = 260F, bottom = 750F, current = false),
                GraphEntry(entry = BookEntry(4), left = 360F, top = 500F, right = 440F, bottom = 750F, current = false),
                GraphEntry(entry = BookEntry(1), left = 170F, top = 0F, right = 270F, bottom = 250F, current = true)
            ),
            listOf()
        )
        whenever(bookRenderer.currentEntryId) doReturn 1
        val underTest = GraphCanvas(mock(), mock())
        underTest.calculate()

        // act
        val (centerX, centerY) = underTest.getCenterOfCurrentGraphEntry()

        // assert
        assertThat(centerX).isEqualTo(220f)
        assertThat(centerY).isEqualTo(125f)
    }

    /**
     *               (One) <--- currentEntry
     *        to two |   | to three
     *      ----------   -----------
     *      |                     |
     *      |     to three        |
     *   (Two) -------------> (Three)
     *      |   <------------     |
     *      |      to two         |
     *      |                     |
     *      --------    -----------
     *      to four|    | to four
     *            (Four)
     */
    @Test
    fun select_touchEntry1_selectEntry1() {
        // arrange
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(
                GraphEntry(entry = BookEntry(1), left = 300f, top = 300f, right = 330f, bottom = 550f, current = true),
                GraphEntry(entry = BookEntry(2), left = 300f, top = 850f, right = 330f, bottom = 1100f),
                GraphEntry(entry = BookEntry(3), left = 630f, top = 850f, right = 680f, bottom = 1100f),
                GraphEntry(entry = BookEntry(4), left = 300f, top = 1400f, right = 340f, bottom = 1650f)
            ), listOf()
        )
        val underTest = GraphCanvas(mock(), mock())
        underTest.calculate()

        // act
        val bookEntry = underTest.touch(315f, 315f)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(1))
    }

    /**
     *               (One) <--- currentEntry
     *        to two |   | to three
     *      ----------   -----------
     *      |                     |
     *      |     to three        |
     *   (Two) -------------> (Three)
     *      |   <------------     |
     *      |      to two         |
     *      |                     |
     *      --------    -----------
     *      to four|    | to four
     *            (Four)
     */
    @Test
    fun select_touchEntry2_selectEntry2() {
        whenever(bookRenderer.render()) doReturn Pair(
            listOf(
                GraphEntry(entry = BookEntry(1), left = 300f, top = 300f, right = 330f, bottom = 550f, current = true),
                GraphEntry(entry = BookEntry(2), left = 300f, top = 850f, right = 330f, bottom = 1100f),
                GraphEntry(entry = BookEntry(3), left = 630f, top = 850f, right = 680f, bottom = 1100f),
                GraphEntry(entry = BookEntry(4), left = 300f, top = 1400f, right = 340f, bottom = 1650f)
            ), listOf()
        )
        val underTest = GraphCanvas(mock(), mock())
        underTest.calculate()

        // act
        val bookEntry = underTest.touch(315f, 900f)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(2))
    }

}