package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

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
    fun `mark way point`() {
        // Arrange
        val underTest = Command.SetWayMark

        // Act
        underTest.execute(game, "WAY_POINT")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Set way mark")
        verify(game).setWayMark("WAY_POINT")

    }

    @Test
    fun `display way points`() {
        // Arrange
        val underTest = Command.WayPoints

        // Act
        underTest.execute(game, "")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Way points")
        verify(game).displayWayPoints()
    }

    @Test
    fun solve() {
        // Arrange
        val underTest = Command.Solve

        // Act
        underTest.execute(game, "")

        // Assert
        assertThat(underTest.toString()).isEqualTo("Solve")
        verify(game).solve()
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
            "Set way mark",
            "Solve",
            "Unvisited",
            "Way points"
        )
    }
}








