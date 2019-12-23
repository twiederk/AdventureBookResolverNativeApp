package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.graphics.Canvas
import android.view.MotionEvent
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

class GraphViewKoinTest : KoinTest {

    private val bookRenderer: BookRenderer by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<BookRenderer>()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun center() {
        // Arrange
        val underTest = GraphView(mock(), mock())

        // Act
        underTest.center(10f, 20f)

        // Assert
        assertThat(underTest.viewportX).isEqualTo(-10f)
        assertThat(underTest.viewportY).isEqualTo(-20f)
    }

    @Test
    fun onTouchEvent_actionDown() {

        // Arrange
        val motionEvent: MotionEvent = mock {
            on { action } doReturn MotionEvent.ACTION_DOWN
            on { x } doReturn 300f
            on { y } doReturn 600f
        }
        val underTest = GraphView(mock(), mock())
        underTest.viewportX = 100f
        underTest.viewportY = 200f

        // Act
        val result = underTest.onTouchEvent(motionEvent)

        // Assert
        assertThat(result).isTrue()
        assertThat(underTest.actionStartX).isEqualTo(200f)
        assertThat(underTest.actionStartY).isEqualTo(400f)
    }


    @Test
    fun onTouchEvent_actionMove() {

        // Arrange
        val motionEvent: MotionEvent = mock {
            on { action } doReturn MotionEvent.ACTION_MOVE
            on { x } doReturn 300f
            on { y } doReturn 600f
        }
        val underTest = GraphView(mock(), mock())
        underTest.actionStartX = 100f
        underTest.actionStartY = 200f

        // Act
        val result = underTest.onTouchEvent(motionEvent)

        // Assert
        assertThat(result).isTrue()
        assertThat(underTest.viewportX).isEqualTo(200f)
        assertThat(underTest.viewportY).isEqualTo(400f)

    }

    // onDraw
    // drawGraphEntry
    // drawGraphEdge
    // setPaintColor

    @Test
    fun onDraw() {
        // Arrange
        val entries = listOf(
            GraphEntry(100f, 200f, 300f, 400f, BookEntry(1), true)
        )
        val edges = listOf(
            GraphEdge(100f, 200f, 300f, 400f, "myLabel", BookEntry(1))
        )
        whenever(bookRenderer.render()) doReturn Pair(entries, edges)

        val canvas: Canvas = mock()

        val underTest = GraphView(mock(), mock())
        underTest.viewportX = 100f
        underTest.viewportY = 200f
        underTest.graphCanvas = mock()

        // Act
        underTest.onDraw(canvas)

        // Assert
        verify(underTest.graphCanvas).translate(canvas, 100f, 200f)
        verify(underTest.graphCanvas).render(canvas, entries, edges)
    }

}