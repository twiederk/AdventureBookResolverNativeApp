package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.graphics.Canvas
import android.view.MotionEvent
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.nhaarman.mockitokotlin2.*
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
    private val game: Game by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
        declareMock<BookRenderer>()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun center_withHeightNotSetOnViewport_viewportXAndYStayAtZero() {
        // Arrange
        val underTest = GraphView(mock(), mock())

        // Act
        underTest.center()

        // Assert
        assertThat(underTest.viewportX).isEqualTo(0f)
        assertThat(underTest.viewportY).isEqualTo(0f)
    }

    @Test
    fun calculateInternal() {
        // arrange
        val underTest = GraphView(mock(), mock())
        whenever(bookRenderer.center()).doReturn(Pair(50f, 100f))

        // act
        underTest.calculateCenter(200f, 400f)

        // assert
        assertThat(underTest.viewportX).isEqualTo(50f)
        assertThat(underTest.viewportY).isEqualTo(100f)
    }

    @Test
    fun onTouchEvent_actionDownOnBookEntry_callGameTouchWithBookEntry() {

        // Arrange
        val motionEvent: MotionEvent = mock {
            on { action } doReturn MotionEvent.ACTION_DOWN
            on { x } doReturn 300f
            on { y } doReturn 600f
        }
        val bookEntry = BookEntry(1)
        whenever(bookRenderer.touch(200f, 400f)).thenReturn(bookEntry)

        val underTest = GraphView(mock(), mock())
        underTest.viewportX = 100f
        underTest.viewportY = 200f

        // Act
        val result = underTest.onTouchEvent(motionEvent)

        // Assert
        assertThat(result).isTrue
        assertThat(underTest.touchX).isEqualTo(200f)
        assertThat(underTest.touchY).isEqualTo(400f)
        verify(bookRenderer).touch(200f, 400f)
        verify(game).touch(bookEntry)
    }

    @Test
    fun onTouchEvent_actionDownOnEmptySpace_storeLocationButDoesNotCallGame() {

        // Arrange
        val motionEvent: MotionEvent = mock {
            on { action } doReturn MotionEvent.ACTION_DOWN
            on { x } doReturn 300f
            on { y } doReturn 600f
        }
        whenever(bookRenderer.touch(200f, 400f)).thenReturn(null)

        val underTest = GraphView(mock(), mock())
        underTest.viewportX = 100f
        underTest.viewportY = 200f

        // Act
        val result = underTest.onTouchEvent(motionEvent)

        // Assert
        assertThat(result).isTrue
        assertThat(underTest.touchX).isEqualTo(200f)
        assertThat(underTest.touchY).isEqualTo(400f)
        verify(bookRenderer).touch(200f, 400f)
        verifyNoMoreInteractions(game)
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
        underTest.touchX = 100f
        underTest.touchY = 200f

        // Act
        val result = underTest.onTouchEvent(motionEvent)

        // Assert
        assertThat(result).isTrue
        assertThat(underTest.viewportX).isEqualTo(200f)
        assertThat(underTest.viewportY).isEqualTo(400f)

    }


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