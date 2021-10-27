package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BookRendererEntryTest {

    @Before
    fun before() {
        GraphPaint.textPaint = mock()
        whenever(GraphPaint.textPaint.measureText(anyString())) doAnswer { (it.arguments[0] as String).length * 10f }
    }

    @Test
    fun render_longTitle_renderWithProperWidth() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("long title of entry")
        }

        // act
        val (entries, _) = BookRenderer(game).render()

        // assert
        assertThat(entries).contains(
            GraphEntry(100f, 100f, 290f, 350f, BookEntry(1), true),
        )
    }

    /**
     *  Set up game with the following book:
     *
     *               (Black hole)
     *        to two |   | to three
     *      ----------   ---------------------------
     *      |                                      |
     *      |                   to three           |
     *   (Parallel Universe) -------------> (Planet Earth)
     */
    @Test
    fun render_graphWithThreeEntries_renderEntriesProperly() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Black hole")
            addAction("to two", 2)
            addAction("to three", 3)
            moveToBookEntry(2)
            setEntryTitle("Parallel universe")
            addAction("to three", 3)
            moveToBookEntry(3)
            setEntryTitle("Planet Earth")
        }

        // act
        val (entries, _) = BookRenderer(game).render()

        // assert
        assertThat(entries).contains(
            GraphEntry(100f, 100f, 200f, 350f, BookEntry(1, "Black hole"), false),
            GraphEntry(100f, 450f, 270f, 700f, BookEntry(2, "Parallel universe"), false),
            GraphEntry(370f, 450f, 490f, 700f, BookEntry(3, "Planet Earth"), true),
        )
    }


}