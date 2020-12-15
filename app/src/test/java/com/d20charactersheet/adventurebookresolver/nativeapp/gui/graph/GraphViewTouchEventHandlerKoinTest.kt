package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.MotionEvent
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.kotlin.*

class GraphViewTouchEventHandlerKoinTest : KoinTest {

    private val bookRenderer: BookRenderer by inject()
    private val game: Game by inject()
    private val entryDialog: EntryDialog by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
        declareMock<BookRenderer>()
        declareMock<EntryDialog>()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun onTouchEvent_actionDownDestinationEntry_moveToDestinationEntry() {

        // Arrange
        val motionEvent: MotionEvent = mock {
            on { action } doReturn MotionEvent.ACTION_DOWN
            on { x } doReturn 300f
            on { y } doReturn 600f
        }
        val bookEntry = BookEntry(1)
        whenever(bookRenderer.touch(200f, 400f)).thenReturn(bookEntry)
        whenever(game.isCurrentEntry(bookEntry)).doReturn(false)

        val graphView = GraphView(mock(), mock())
        graphView.viewportX = 100f
        graphView.viewportY = 200f
        val underTest = GraphViewTouchEventHandler()

        // Act
        val result = underTest.onTouchEvent(graphView, motionEvent)

        // Assert
        assertThat(result).isTrue
        assertThat(graphView.touchX).isEqualTo(200f)
        assertThat(graphView.touchY).isEqualTo(400f)
        verify(game).move(bookEntry.id)
    }

    @Test
    fun onTouchEvent_actionDownOnCurrentEntry_openEntryDialog() {

        // Arrange
        val motionEvent: MotionEvent = mock {
            on { action } doReturn MotionEvent.ACTION_DOWN
            on { x } doReturn 300f
            on { y } doReturn 600f
        }
        val bookEntry = BookEntry(1)
        whenever(bookRenderer.touch(any(), any())).thenReturn(bookEntry)
        whenever(game.isCurrentEntry(bookEntry)).doReturn(true)

        val graphView: GraphView = mock()
        whenever(graphView.viewportX).doReturn(100f)
        whenever(graphView.viewportY).doReturn(200f)
        whenever(graphView.context).doReturn(mock())
        val underTest = GraphViewTouchEventHandler()

        // Act
        val result = underTest.onTouchEvent(graphView, motionEvent)

        // Assert
        assertThat(result).isTrue
        verify(entryDialog).show(any())
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

        val graphView = GraphView(mock(), mock())
        graphView.viewportX = 100f
        graphView.viewportY = 200f
        val underTest = GraphViewTouchEventHandler()

        // Act
        val result = underTest.onTouchEvent(graphView, motionEvent)

        // Assert
        assertThat(result).isTrue
        assertThat(graphView.touchX).isEqualTo(200f)
        assertThat(graphView.touchY).isEqualTo(400f)
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
        val graphView = GraphView(mock(), mock())
        graphView.touchX = 100f
        graphView.touchY = 200f
        val underTest = GraphViewTouchEventHandler()

        // Act
        val result = underTest.onTouchEvent(graphView, motionEvent)

        // Assert
        assertThat(result).isTrue
        assertThat(graphView.viewportX).isEqualTo(200f)
        assertThat(graphView.viewportY).isEqualTo(400f)

    }

}