package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.ToolbarPanel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.kotlin.*

class ExecuteOnClickListenerKoinTest : KoinTest {

    private val game: Game by inject()
    private val toolbarPanel: ToolbarPanel by inject()
    private val genericCommandPanel: GenericCommandPanel by inject()

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
    fun `onClick executes command`() {
        // Arrange
        declareMock<Game> {
            whenever(createBook("myArgs")).doReturn("book created")
        }
        declareMock<ToolbarPanel>()

        declareMock<GenericCommandPanel> {
            whenever(getSelectedCommand()).doReturn(Command.Create)
            whenever(getArgument()).doReturn("myArgs")
        }

        // Act
        ExecuteOnClickListener().onClick(mock())

        // Assert
        verify(game).createBook("myArgs")
        verify(genericCommandPanel).appendOutput("book created")
        verify(toolbarPanel).update()
    }

    @Test
    fun `onClick command throws exception`() {
        // Arrange
        declareMock<Game> {
            whenever(createBook("myArgs")).doThrow(IllegalStateException("myException"))
        }
        declareMock<ToolbarPanel>()

        declareMock<GenericCommandPanel> {
            whenever(getSelectedCommand()).doReturn(Command.Create)
            whenever(getArgument()).doReturn("myArgs")
        }

        // Act
        ExecuteOnClickListener().onClick(mock())

        // Assert
        verify(game).createBook("myArgs")
        verify(genericCommandPanel).appendOutput("myException")
    }


}