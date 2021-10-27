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


class BookRendererScaleTest {

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
    fun center_withScale2_centerOnCurrentEntry() {
        // arrange
        underTest.scale = 2F
        underTest.render()

        // act
        val (viewportX, viewportY) = underTest.center()

        // assert
        assertThat(viewportX).isEqualTo(260f)
        assertThat(viewportY).isEqualTo(450f)
    }

    @Test
    fun select_withScale2touchEntry1_selectEntry1() {
        // arrange
        underTest.scale = 2F
        underTest.render()

        // act
        val bookEntry = underTest.touch(250f, 300f)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(1))
    }

    @Test
    fun select_withScale2touchEntry2_selectEntry2() {
        // arrange
        underTest.scale = 2F
        underTest.render()

        // act
        val bookEntry = underTest.touch(250f, 1000f)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(2))
    }

    @Test
    fun render_withScale2_everythingIsDoubled() {
        // arrange
        underTest.scale = 2F

        // act
        val (entries, edges) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(200f, 200f, 260f, 700f, BookEntry(1), true),
            GraphEntry(200f, 900f, 260f, 1400f, BookEntry(2)),
            GraphEntry(460f, 900f, 560f, 1400f, BookEntry(3)),
            GraphEntry(200f, 1600f, 280f, 2100f, BookEntry(4))
        )

        assertThat(edges).contains(
            GraphEdge(260f, 700f, 260f, 900f, "to two", BookEntry(2)),
            GraphEdge(260f, 700f, 520f, 900f, "to three", BookEntry(3)),
            GraphEdge(260f, 1400f, 260f, 1600f, "to four", BookEntry(4)),
            GraphEdge(260f, 1400f, 520f, 900f, "to three", BookEntry(3)),
            GraphEdge(560f, 1400f, 300f, 900f, "to two", BookEntry(2)),
            GraphEdge(560f, 1400f, 300f, 1600f, "to four", BookEntry(4))
        )

    }

}