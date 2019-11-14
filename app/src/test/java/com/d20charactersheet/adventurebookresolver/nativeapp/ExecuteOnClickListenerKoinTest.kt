package com.d20charactersheet.adventurebookresolver.nativeapp

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

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
    fun `execute button onClick executes command`() {
        // Arrange
        declareMock<Game> {
            given { this.createBook("myArgs") }.willReturn("book created")
        }
        declareMock<ToolbarPanel>()
        declareMock<AttributesPanel>()
        declareMock<BookPanel>()
        declareMock<EntryPanel>()
        declareMock<ActionPanel>()
        declareMock<InventoryPanel>()

        declareMock<GenericCommandPanel> {
            given { getSelectedCommand() }.willReturn(Command.Create)
            given { getArgument() }.willReturn("myArgs")
        }

        // Act
        ExecuteOnClickListener().onClick(mock())

        // Assert
        verify(game).createBook("myArgs")
        verify(genericCommandPanel).appendOutput("book created")
        verify(toolbarPanel).update()
    }


}