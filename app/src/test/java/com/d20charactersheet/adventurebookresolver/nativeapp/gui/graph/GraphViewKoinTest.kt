package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.graphics.Canvas
import android.view.MotionEvent
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
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
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GraphViewKoinTest : KoinTest {

    private val bookRenderer: TraversalBookRenderer by inject()
    private val touchEventHandler: GraphViewTouchEventHandler by inject()

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
        declareMock<GraphViewTouchEventHandler>()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun center_withHeightNotSetOnViewport_viewportXAndYStayAtZero() {
        // Arrange
        val underTest = GraphView(mock())

        // Act
        underTest.guardedCenterOnCurrentBookEntry()

        // Assert
        assertThat(underTest.viewportX).isEqualTo(0f)
        assertThat(underTest.viewportY).isEqualTo(0f)
    }

    @Test
    fun centerOnBookEntry() {
        // arrange
        val underTest = GraphView(mock())
        val graphCanvas: GraphCanvas = mock()
        whenever(graphCanvas.getCenterOfCurrentGraphEntry()).doReturn(Pair(50f, 100f))
        underTest.graphCanvas = graphCanvas

        // act
        underTest.centerOnCurrentBookEntry(200f, 400f)

        // assert
        assertThat(underTest.viewportX).isEqualTo(50f)
        assertThat(underTest.viewportY).isEqualTo(100f)
    }

    @Test
    fun onTouchEvent() {
        // arrange
        val motionEvent: MotionEvent = mock()
        val graphCanvas: GraphCanvas = mock()
        val underTest = GraphView(mock())
        underTest.graphCanvas = graphCanvas
        underTest.setOnTouchListener(touchEventHandler)

        // act
        underTest.onTouchEvent(motionEvent)

        // assert
        verify(touchEventHandler).onTouchEvent(underTest, graphCanvas, motionEvent)
    }

    @Test
    fun onDraw() {
        // Arrange
        val entries = listOf(
            GraphEntry(entry = BookEntry(1), left = 100f, top = 200f, right = 300f, bottom = 400f, current = true)
        )
        val edges = listOf(
            GraphEdge(100f, 200f, 300f, 400f, "myLabel", 0f, 0f, BookEntry(1))
        )
        whenever(bookRenderer.render()) doReturn Pair(entries, edges)

        val canvas: Canvas = mock()

        val underTest = GraphView(mock())
        underTest.viewportX = 100f
        underTest.viewportY = 200f
        underTest.graphCanvas = mock()

        // Act
        underTest.onDraw(canvas)

        // Assert
        inOrder(underTest.graphCanvas) {
            verify(underTest.graphCanvas).calculate()
            verify(underTest.graphCanvas).translate(canvas, 100f, 200f)
            verify(underTest.graphCanvas).render(canvas)
        }
    }

    @Test
    fun createBitmap_bitmapLargerThan10MB_throwIllegalArgumentException() {
        // arrange
        val graphCanvas: GraphCanvas = mock()
        whenever(graphCanvas.maxRight).thenReturn(10_485_760F)
        whenever(graphCanvas.maxBottom).thenReturn(10F)

        val underTest = GraphView(mock())
        underTest.graphCanvas = graphCanvas

        // act
        val thrown = catchThrowable { underTest.createBitmap() }

        // assert
        assertThat(thrown).isInstanceOf(IllegalStateException::class.java).hasMessage("bitmap to large (>10 MB)")
    }

}