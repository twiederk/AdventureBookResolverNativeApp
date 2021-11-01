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

class DepthBookRendererWith3EntriesTest {

    private lateinit var underTest: DepthBookRenderer

    /**
     *  Set up game with the following book:
     *
     *               (Black hole)
     *        to two |         | to three
     *      ----------         ---------------------
     *      |                                      |
     *      |                   to three           |
     *   (Parallel Universe) -------------> (Planet Earth)
     */
    @Before
    fun before() {
        GraphPaint.textPaint = mock()
        whenever(GraphPaint.textPaint.measureText(anyString())) doAnswer { (it.arguments[0] as String).length * 10f }

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

        underTest = DepthBookRenderer(game)

    }

    @Test
    fun render_longTitle_renderWithProperWidth() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("long title of entry")
        }

        // act
        val (entries, _) = DepthBookRenderer(game).render()

        // assert
        assertThat(entries).contains(
            GraphEntry(entry = BookEntry(1), left = 300f, top = 300f, right = 490f, bottom = 550f, current = true),
        )
    }

    @Test
    fun render_graphEntries_coordinatesOfEntries() {

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(entry = BookEntry(1, "Black hole"), left = 300f, top = 300f, right = 400f, bottom = 550f, current = false),
            GraphEntry(entry = BookEntry(2, "Parallel universe"), left = 300f, top = 850f, right = 470f, bottom = 1100f, current = false),
            GraphEntry(entry = BookEntry(3, "Planet Earth"), left = 770f, top = 850f, right = 890f, bottom = 1100f, current = true),
        )
    }

    @Test

    fun render_graphEdges_coordinatesOfEdges() {

        // act
        val (_, edges) = underTest.render()

        // assert
        assertThat(edges).contains(
            GraphEdge(350f, 550f, 385f, 850f, "to two", 367.5f, 700f, BookEntry(2)),
            GraphEdge(350f, 550f, 830f, 850f, "to three", 590f, 700f, BookEntry(3)),
            GraphEdge(385f, 1100f, 830f, 850f, "to three", 607.5f, 975f, BookEntry(3)),
        )
    }

}