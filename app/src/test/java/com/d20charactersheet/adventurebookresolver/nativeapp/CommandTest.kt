package com.d20charactersheet.adventurebookresolver.nativeapp

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class CommandTest {

    private val game = mock<Game>()

    @Test
    fun `display actions to unvisited entries`() {
        // Arrange
        val underTest = Command.Unvisited

        // Act
        underTest.execute(game, "")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Unvisited")
        verify(game).displayActionsToUnvisitedEntries()
    }

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
    fun search() {
        // Arrange
        val underTest = Command.Search

        // Act
        underTest.execute(game, "start")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Search")
        verify(game).search("start")
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
    fun load() {
        // Arrange
        val underTest = Command.Load

        // Act
        underTest.execute(game, "loadBook")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Load")
        verify(game).loadBook("loadBook")
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
            "Load",
            "Path",
            "Roll die",
            "Run",
            "Search",
            "Unvisited"
        )
    }


}








