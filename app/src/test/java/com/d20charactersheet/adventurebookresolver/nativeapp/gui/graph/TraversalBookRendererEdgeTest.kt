package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class TraversalBookRendererEdgeTest {

    @Before
    fun before() {
        GraphPaint.textPaint = mock()
        whenever(GraphPaint.textPaint.measureText(ArgumentMatchers.anyString())) doAnswer { (it.arguments[0] as String).length * 10f }
    }

    /**
     *  (1 - Entry Hall)
     */
    @Test
    fun render_rootEntryOnly_renderNoEdges() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
        }

        // act
        val (_, edges) = TraversalBookRenderer(game).render()

        // assert
        assertThat(edges).isEmpty()
    }

    /**
     *  (1 - Entry Hall)
     *          |
     *          | to throne
     *          |
     *   (2 - Untitled)
     */
    @Test
    fun render_parentWithSmallerChild_renderOneEdge() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)

        }

        // act
        val (_, edges) = TraversalBookRenderer(game).render()

        // assert
        assertThat(edges).containsExactly(
            GraphEdge(startX = 50F, startY = 250F, endX = 50F, endY = 500F, "to throne", labelX = 50F, labelY = 375F, BookEntry(2))
        )
    }

    /**
     *               (1 - Entry Hall)
     *         to throne |        | to library
     *          ----------         -----------
     *          |                            |
     *   (2 - Untitled)                  (3 - Untitled)
     */
    @Test
    fun render_oneParentWithTwoChildren_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
            addAction("to library", 3)
        }

        GraphEntry(entry = BookEntry(2), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false)
        GraphEntry(entry = BookEntry(3), left = 180F, top = 500F, right = 260F, bottom = 750F, current = false)
        GraphEntry(entry = BookEntry(1), left = 80F, top = 0F, right = 180F, bottom = 250F, current = true)

        // act
        val (_, edges) = TraversalBookRenderer(game).render()

        // assert
        assertThat(edges).containsExactly(
            GraphEdge(startX = 130F, startY = 250F, endX = 40F, endY = 500F, "to throne", labelX = 85F, labelY = 375F, BookEntry(2)),
            GraphEdge(startX = 130F, startY = 250F, endX = 220F, endY = 500F, "to library", labelX = 175F, labelY = 375F, BookEntry(3)),
        )

    }

}