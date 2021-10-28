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


class BookRendererWith4EntriesTest {

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
    fun render_graphEntries_coordinatesOfEntries() {
        // arrange
        val scale = 1F
        underTest.scale = scale

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(300f * scale, 300f * scale, 330f * scale, 550f * scale, BookEntry(1), true),
            GraphEntry(300f * scale, 850f * scale, 330f * scale, 1100f * scale, BookEntry(2)),
            GraphEntry(630f * scale, 850f * scale, 680f * scale, 1100f * scale, BookEntry(3)),
            GraphEntry(300f * scale, 1400f * scale, 340f * scale, 1650f * scale, BookEntry(4))
        )

    }

    @Test
    fun render_graphEntriesWithScale2_coordinatesOfEntries() {

        // arrange
        val scale = 2F
        underTest.scale = scale

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(300f * scale, 300f * scale, 330f * scale, 550f * scale, BookEntry(1), true),
            GraphEntry(300f * scale, 850f * scale, 330f * scale, 1100f * scale, BookEntry(2)),
            GraphEntry(630f * scale, 850f * scale, 680f * scale, 1100f * scale, BookEntry(3)),
            GraphEntry(300f * scale, 1400f * scale, 340f * scale, 1650f * scale, BookEntry(4))
        )

    }

    @Test
    fun render_graphEdges_coordinatesOfEdges() {

        // arrange
        val scale = 1F
        underTest.scale = scale

        // act
        val (_, edges) = underTest.render()

        // assert
        assertThat(edges).contains(
            GraphEdge(315f * scale, 550f * scale, 315f * scale, 850f * scale, "to two", 315f * scale, 700f * scale, BookEntry(2)),
            GraphEdge(315f * scale, 550f * scale, 655f * scale, 850f * scale, "to three", 485f * scale, 700f * scale, BookEntry(3)),
            GraphEdge(315f * scale, 1100f * scale, 320f * scale, 1400f * scale, "to four", 317.5f * scale, 1250f * scale, BookEntry(4)),
            GraphEdge(315f * scale, 1100f * scale, 655f * scale, 850f * scale, "to three", 485f * scale, 975f * scale, BookEntry(3)),
            GraphEdge(655f * scale, 1100f * scale, 315f * scale, 850f * scale, "to two", 485f * scale, 975f * scale, BookEntry(2)),
            GraphEdge(655f * scale, 1100f * scale, 320f * scale, 1400f * scale, "to four", 487.5f * scale, 1250f * scale, BookEntry(4))
        )

    }

    @Test
    fun render_graphEdgesWithScale2_coordinatesOfEdges() {

        // arrange
        val scale = 2F
        underTest.scale = scale

        // act
        val (_, edges) = underTest.render()

        // assert
        assertThat(edges).contains(
            GraphEdge(315f * scale, 550f * scale, 315f * scale, 850f * scale, "to two", 315f * scale, 700f * scale, BookEntry(2)),
            GraphEdge(315f * scale, 550f * scale, 655f * scale, 850f * scale, "to three", 485f * scale, 700f * scale, BookEntry(3)),
            GraphEdge(315f * scale, 1100f * scale, 320f * scale, 1400f * scale, "to four", 317.5f * scale, 1250f * scale, BookEntry(4)),
            GraphEdge(315f * scale, 1100f * scale, 655f * scale, 850f * scale, "to three", 485f * scale, 975f * scale, BookEntry(3)),
            GraphEdge(655f * scale, 1100f * scale, 315f * scale, 850f * scale, "to two", 485f * scale, 975f * scale, BookEntry(2)),
            GraphEdge(655f * scale, 1100f * scale, 320f * scale, 1400f * scale, "to four", 487.5f * scale, 1250f * scale, BookEntry(4))
        )

    }

    @Test
    fun center_centerOnCurrentEntry_coordinatesOfCenter() {
        // arrange
        val scale = 1F
        underTest.scale = scale
        underTest.render()

        // act
        val (viewportX, viewportY) = underTest.center()

        // assert
        assertThat(viewportX).isEqualTo(315f * scale)
        assertThat(viewportY).isEqualTo(425f * scale)
    }

    @Test
    fun center_centerOnCurrentEntryWithScale2_coordinatesOfCenter() {
        // arrange
        val scale = 1F
        underTest.scale = scale
        underTest.render()

        // act
        val (viewportX, viewportY) = underTest.center()

        // assert
        assertThat(viewportX).isEqualTo(315f * scale)
        assertThat(viewportY).isEqualTo(425f * scale)
    }


    @Test
    fun select_touchEntry1_selectEntry1() {
        // arrange
        val scale = 1F
        underTest.scale = scale
        underTest.render()

        // act
        val bookEntry = underTest.touch(315f * scale, 315f * scale)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(1))
    }

    @Test
    fun select_touchEntry1withScale2_selectEntry1() {
        // arrange
        val scale = 2F
        underTest.scale = scale
        underTest.render()

        // act
        val bookEntry = underTest.touch(315f * scale, 315f * scale)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(1))
    }

    @Test
    fun select_touchEntry2_selectEntry2() {
        // arrange
        val scale = 1F
        underTest.scale = scale
        underTest.render()

        // act
        val bookEntry = underTest.touch(315f * scale, 900f * scale)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(2))
    }

    @Test
    fun select_touchEntry2withScale2_selectEntry2() {
        // arrange
        val scale = 2F
        underTest.scale = scale
        underTest.render()

        // act
        val bookEntry = underTest.touch(315f * scale, 900f * scale)

        // assert
        assertThat(bookEntry).isEqualTo(BookEntry(2))
    }

}