package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.BookPanel
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

class ActionAddOnClickListenerKoinTest : KoinTest {

    private val game: Game by inject()
    private val actionPanel: ActionPanel by inject()
    private val bookPanel: BookPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `if label is empty do not add an action`() {
        // Arrange
        declareMock<BookPanel>()
        declareMock<ActionPanel> {
            whenever(getActionLabel()).doReturn("")
            whenever(getActionId()).doReturn("10")
        }

        // Act
        ActionAddOnClickListener().onClick(mock())

        // Assert
        verify(actionPanel).getActionLabel()
        verify(actionPanel).getActionId()
        verifyNoMoreInteractions(actionPanel, bookPanel)
    }

    @Test
    fun `if id is empty do not add an action`() {
        // Arrange
        declareMock<BookPanel>()
        declareMock<ActionPanel> {
            whenever(getActionLabel()).doReturn("myActionLabel")
            whenever(getActionId()).doReturn("")
        }

        // Act
        ActionAddOnClickListener().onClick(mock())

        // Assert
        verify(actionPanel).getActionLabel()
        verify(actionPanel).getActionId()
        verifyNoMoreInteractions(actionPanel, bookPanel)
    }

    @Test
    fun `add action`() {
        // Arrange
        declareMock<Game>()
        declareMock<BookPanel>()
        declareMock<ActionPanel> {
            whenever(getActionLabel()).doReturn("myActionLabel")
            whenever(getActionId()).doReturn("10")
        }

        // Act
        ActionAddOnClickListener().onClick(mock())

        // Assert
        verify(game).addAction("myActionLabel", 10)
        verify(actionPanel).clear()
        verify(actionPanel).update()
    }


}