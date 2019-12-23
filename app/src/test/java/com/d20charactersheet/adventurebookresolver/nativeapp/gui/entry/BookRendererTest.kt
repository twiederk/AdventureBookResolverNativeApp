package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test


class BookRendererTest {

    private lateinit var underTest: BookRenderer

    @Before
    fun setUp() {
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("One")
            addAction("to two", 2)
            addAction("to three", 3)
            moveToBookEntry(2)
            setEntryTitle("Two")
            setEntryWayMark(WayMark.WAY_POINT)
            addAction("to four", 4)
            addAction("to three", 3)
            moveToBookEntry(4)
            setEntryTitle("Four")
            setEntryWayMark(WayMark.WAY_POINT)
            restart()
            moveToBookEntry(3)
            setEntryTitle("Three")
            addAction("to two", 2)
            addAction("to four", 4)
            setEntryWayMark(WayMark.WAY_POINT)
            restart()
        }
        underTest = BookRenderer(game)
    }

    @Test
    fun render() {

        // Act
        val (entries, edges) = underTest.render()

        // Assert
        assertThat(entries).contains(
            GraphEntry(100f, 100f, 350f, 350f, BookEntry(1), true),
            GraphEntry(100f, 450f, 350f, 700f, BookEntry(2)),
            GraphEntry(450f, 450f, 700f, 700f, BookEntry(3)),
            GraphEntry(100f, 800f, 350f, 1050f, BookEntry(4))
        )

        assertThat(edges).contains(
            GraphEdge(225f, 350f, 225f, 450f, "to two", BookEntry(2)),
            GraphEdge(225f, 350f, 575f, 450f, "to three", BookEntry(3)),
            GraphEdge(225f, 700f, 575f, 450f, "to three", BookEntry(3)),
            GraphEdge(575f, 700f, 225f, 450f, "to two", BookEntry(2)),
            GraphEdge(225f, 700f, 225f, 800f, "to four", BookEntry(4)),
            GraphEdge(575f, 700f, 225f, 800f, "to four", BookEntry(4))
        )

    }

    @Test
    fun center_onCurrentEntry() {
        // Arrange
        underTest.render()

        // Act
        val (viewportX, viewportY) = underTest.center()

        // Assert
        assertThat(viewportX).isEqualTo(225f)
        assertThat(viewportY).isEqualTo(225f)
    }

    @Test
    fun center_currentEntryNotFound() {
        // Act
        val (viewportX, viewportY) = underTest.center()

        // Assert
        assertThat(viewportX).isEqualTo(125f)
        assertThat(viewportY).isEqualTo(125f)
    }

}