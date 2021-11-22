package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class DepthBookRendererWith4EntriesTest {

    private lateinit var underTest: DepthBookRenderer

    /**
     *  Set up game with the following book:
     *
     *               (One) <--- currentEntry
     *        to two |   | to three
     *      ----------   -----------
     *      |                     |
     *      |     to three        |
     *   (Two*) -------------> (Three*)
     *      |   <------------     |
     *      |      to two         |
     *      |                     |
     *      --------    -----------
     *      to four|    | to four
     *            (Four*)
     *
     * (*) = wap point
     */
    @Before
    fun setUp() {
        GraphPaint.textPaint = mock()
        whenever(GraphPaint.textPaint.measureText(ArgumentMatchers.anyString())) doAnswer { (it.arguments[0] as String).length * 10f }

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
        underTest = DepthBookRenderer(game)
    }

    @Test
    fun render_graphEntries_coordinatesOfEntries() {

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(entry = BookEntry(1), left = 300f, top = 300f, right = 330f, bottom = 550f, current = true),
            GraphEntry(entry = BookEntry(2), left = 300f, top = 850f, right = 330f, bottom = 1100f),
            GraphEntry(entry = BookEntry(3), left = 630f, top = 850f, right = 680f, bottom = 1100f),
            GraphEntry(entry = BookEntry(4), left = 300f, top = 1400f, right = 340f, bottom = 1650f)
        )

    }

    @Test
    fun render_graphEdges_coordinatesOfEdges() {

        // act
        val (_, edges) = underTest.render()

        // assert
        assertThat(edges).contains(
            GraphEdge(315f, 550f, 315f, 850f, "to two", 285f, 700f, BookEntry(2)),
            GraphEdge(315f, 550f, 655f, 850f, "to three", 445f, 800f, BookEntry(3)),
            GraphEdge(315f, 1100f, 320f, 1400f, "to four", 282.5f, 1250f, BookEntry(4)),
            GraphEdge(315f, 1100f, 655f, 850f, "to three", 445f, 1075f, BookEntry(3)),
            GraphEdge(655f, 1100f, 315f, 850f, "to two", 455f, 975f, BookEntry(2)),
            GraphEdge(655f, 1100f, 320f, 1400f, "to four", 452.5f, 1350f, BookEntry(4))
        )

    }

}