package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.ToolbarPanel
import kotlinx.coroutines.runBlocking
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
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ExecuteOnClickListenerKoinTest : KoinTest {

    private val game: Game by inject()
    private val toolbarPanel: ToolbarPanel by inject()
    private val genericCommandPanel: GenericCommandPanel by inject()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

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
        verify(genericCommandPanel).clearOutput()
        verify(game).createBook("myArgs")
        verify(genericCommandPanel).appendOutput("book created")
        verify(toolbarPanel).update()
    }

    @Test
    fun `onClick command throws exception`() = runBlocking {
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