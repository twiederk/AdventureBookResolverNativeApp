package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.MotionEvent
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
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
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class GraphViewTouchEventHandlerKoinTest : KoinTest {

    private val game: Game by inject()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
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
        val graphCanvas: GraphCanvas = mock()
        whenever(graphCanvas.touch(200f, 400f)).thenReturn(bookEntry)
        whenever(game.isCurrentEntry(bookEntry)).doReturn(false)
        whenever(game.isEntryOfNextEntries(bookEntry)).doReturn(true)

        val graphView = GraphView(mock(), mock())
        graphView.graphCanvas = graphCanvas
        graphView.viewportX = 100f
        graphView.viewportY = 200f
        graphView.renderMode = RenderMode.FREE
        val underTest = GraphViewTouchEventHandler()

        // Act
        underTest.onTouchEvent(graphView, graphCanvas, motionEvent)

        // Assert
        assertThat(graphView.touchX).isEqualTo(200f)
        assertThat(graphView.touchY).isEqualTo(400f)
        assertThat(graphView.renderMode).isEqualTo(RenderMode.CENTER)
        verify(game).move(bookEntry.id)
    }

    @Test
    fun onTouchEvent_actionDownOnEmptySpace_storeLocationButDoesNotCallGame() {

        // Arrange
        val motionEvent: MotionEvent = mock {
            on { action } doReturn MotionEvent.ACTION_DOWN
            on { x } doReturn 300f
            on { y } doReturn 600f
        }
        val graphCanvas: GraphCanvas = mock()
        whenever(graphCanvas.touch(200f, 400f)).thenReturn(null)

        val graphView = GraphView(mock(), mock())
        graphView.graphCanvas = graphCanvas
        graphView.viewportX = 100f
        graphView.viewportY = 200f
        val underTest = GraphViewTouchEventHandler()

        // Act
        underTest.onTouchEvent(graphView, graphCanvas, motionEvent)

        // Assert
        assertThat(graphView.touchX).isEqualTo(200f)
        assertThat(graphView.touchY).isEqualTo(400f)
        verify(graphCanvas).touch(200f, 400f)
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
        underTest.onTouchEvent(graphView, mock(), motionEvent)

        // Assert
        assertThat(graphView.viewportX).isEqualTo(200f)
        assertThat(graphView.viewportY).isEqualTo(400f)
        assertThat(graphView.renderMode).isEqualTo(RenderMode.FREE)
    }

}