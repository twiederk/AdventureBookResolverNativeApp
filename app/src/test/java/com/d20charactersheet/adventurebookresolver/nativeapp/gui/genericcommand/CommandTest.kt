package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class CommandTest {

    private val game = mock<Game>()

    @Test
    fun `display path`() {
        // Arrange
        val underTest = Command.Path

        // Act
        underTest.execute(game, "")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Path")
        verify(game).displayPath()
    }

    @Test
    fun `run to entry`() {
        // Arrange
        val underTest = Command.Run

        // Act
        underTest.execute(game, "100")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Run")
        verify(game).runTo("100")
    }

    @Test
    fun create() {
        // Arrange
        val underTest = Command.Create

        // Act
        underTest.execute(game, "my new book")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Create")
        verify(game).createBook("my new book")
    }

    @Test
    fun rollDie() {
        // Arrange
        val underTest = Command.RollDie

        // Act
        underTest.execute(game, "2d6+3")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Roll die")
        verify(game).rollDie("2d6+3")
    }

    @Test
    fun `list of all commands sorted by name`() {
        // Act
        val items = Command.sortedValues()

        // Assert
        assertThat(items.map { it.toString() }).containsExactly(
            "Create",
            "Path",
            "Roll die",
            "Run",
            "Search",
            "Solve",
            "Unvisited",
            "Way points"
        )
    }
}








