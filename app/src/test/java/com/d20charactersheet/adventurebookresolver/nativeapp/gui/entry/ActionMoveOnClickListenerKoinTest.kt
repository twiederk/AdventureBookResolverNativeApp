package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.widget.Button
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.nhaarman.mockitokotlin2.doReturn
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

class ActionMoveOnClickListenerKoinTest : KoinTest {

    private val game: Game by inject()
    private val entryPanel: EntryPanel by inject()
    private val actionPanel: ActionPanel by inject()
    private val graphPanel: GraphPanel by inject()

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
    fun `ActionMoveOnClickListener onClick`() {
        // Arrange
        declareMock<Game>()
        declareMock<EntryPanel>()
        declareMock<ActionPanel>()
        val actionMoveEntryIdButton: Button = mock {
            on { text } doReturn "100"
        }
        declareMock<GraphPanel>()

        // Act
        ActionMoveOnClickListener().onClick(actionMoveEntryIdButton)

        // Assert
        verify(game).move(100)
        verify(entryPanel).update()
        verify(actionPanel).update()
        verify(graphPanel).update()
    }

}