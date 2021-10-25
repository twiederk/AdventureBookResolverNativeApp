package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test


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
    fun center_instantiation_centerOnCurrentEntry() {
        // arrange
        underTest.render()

        // act
        val (viewportX, viewportY) = underTest.center()

        // assert
        assertThat(viewportX).isEqualTo(225f)
        assertThat(viewportY).isEqualTo(225f)
    }

    @Test
    fun center_withScale2_centerOnCurrentEntry() {
        // arrange
        underTest.scale = 2F
        underTest.render()

        // act
        val (viewportX, viewportY) = underTest.center()

        // assert
        assertThat(viewportX).isEqualTo(450f)
        assertThat(viewportY).isEqualTo(450f)
    }

    @Test
    fun select_touchEntry2_selectEntry2() {
        // arrange
        underTest.render()

        // act
        val bookEntry = underTest.touch(150f, 150f)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(1))
    }

    @Test
    fun select_withScale2touchEntry2_selectEntry2() {
        // arrange
        underTest.scale = 2F
        underTest.render()

        // act
        val bookEntry = underTest.touch(300f, 300f)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(1))
    }

    @Test
    fun render_withScale2_everythingIsDoubled() {
        // arrange
        underTest.scale = 2F

        // act
        val (entries, edges) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(200f, 200f, 700f, 700f, BookEntry(1), true),
            GraphEntry(200f, 900f, 700f, 1400f, BookEntry(2)),
            GraphEntry(900f, 900f, 1400f, 1400f, BookEntry(3)),
            GraphEntry(200f, 1600f, 700f, 2100f, BookEntry(4))
        )

        assertThat(edges).contains(
            GraphEdge(450f, 700f, 450f, 900f, "to two", BookEntry(2)),
            GraphEdge(450f, 700f, 1150f, 900f, "to three", BookEntry(3)),
            GraphEdge(450f, 1400f, 1150f, 900f, "to three", BookEntry(3)),
            GraphEdge(1150f, 1400f, 450f, 900f, "to two", BookEntry(2)),
            GraphEdge(450f, 1400f, 450f, 1600f, "to four", BookEntry(4)),
            GraphEdge(1150f, 1400f, 450f, 1600f, "to four", BookEntry(4))
        )

    }

}