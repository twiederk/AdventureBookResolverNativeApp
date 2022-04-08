package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CommandExecutorTest {

    @Test
    fun `should execute command in main thread`() {
        // arrange
        val game: Game = mock()

        // act
        CommandExecutor().execute(Command.Create, "myNewBook", game, mock())

        // assert
        verify(game).createBook("myNewBook")
    }

}