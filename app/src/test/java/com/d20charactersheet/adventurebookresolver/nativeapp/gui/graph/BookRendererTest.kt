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


class BookRendererTest {

    private lateinit var underTest: BookRenderer

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
        underTest = BookRenderer(game)
    }

    @Test
    fun render() {

        // act
        val (entries, edges) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(100f, 100f, 130f, 350f, BookEntry(1), true),
            GraphEntry(100f, 450f, 130f, 700f, BookEntry(2)),
            GraphEntry(230f, 450f, 280f, 700f, BookEntry(3)),
            GraphEntry(100f, 800f, 140f, 1050f, BookEntry(4))
        )

        assertThat(edges).contains(
            GraphEdge(115f, 350f, 115f, 450f, "to two", BookEntry(2)),
            GraphEdge(115f, 350f, 245f, 450f, "to three", BookEntry(3)),
            GraphEdge(115f, 700f, 115f, 800f, "to four", BookEntry(4)),
            GraphEdge(115f, 700f, 245f, 450f, "to three", BookEntry(3)),
            GraphEdge(255f, 700f, 125f, 450f, "to two", BookEntry(2)),
            GraphEdge(255f, 700f, 125f, 800f, "to four", BookEntry(4))
        )

    }

    @Test
    fun center_instantiation_centerOnCurrentEntry() {
        // arrange
        underTest.render()

        // act
        val (viewportX, viewportY) = underTest.center()

        // assert
        assertThat(viewportX).isEqualTo(115f)
        assertThat(viewportY).isEqualTo(225f)
    }

    @Test
    fun select_touchEntry1_selectEntry1() {
        // arrange
        underTest.render()

        // act
        val bookEntry = underTest.touch(115f, 115f)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(1))
    }

    @Test
    fun select_touchEntry2_selectEntry2() {
        // arrange
        underTest.render()

        // act
        val bookEntry = underTest.touch(115f, 500f)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(2))
    }

}