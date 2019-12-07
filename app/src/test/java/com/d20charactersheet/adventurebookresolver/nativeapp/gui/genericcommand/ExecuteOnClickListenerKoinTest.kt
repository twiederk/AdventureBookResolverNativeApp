package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.ToolbarPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.AttributesPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.BookPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry.ActionPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry.EntryPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.ItemPanel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
            whenever(createBook("myArgs")).doReturn("book created")
        }
        declareMock<ToolbarPanel>()
        declareMock<AttributesPanel>()
        declareMock<BookPanel>()
        declareMock<EntryPanel>()
        declareMock<ActionPanel>()
        declareMock<ItemPanel>()

        declareMock<GenericCommandPanel> {
            whenever(getSelectedCommand()).doReturn(Command.Create)
            whenever(getArgument()).doReturn("myArgs")
        }

        // Act
        ExecuteOnClickListener()
            .onClick(mock())

        // Assert
        verify(game).createBook("myArgs")
        verify(genericCommandPanel).appendOutput("book created")
        verify(toolbarPanel).update()
    }


}