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

class TraversalBookRendererEntryTest {

    @Before
    fun before() {
        GraphPaint.textPaint = mock()
        whenever(GraphPaint.textPaint.measureText(ArgumentMatchers.anyString())) doAnswer { (it.arguments[0] as String).length * 10f }
    }

    /**
     *  (1 - Entry Hall)
     */
    @Test
    fun render_rootEntryOnly_renderRootEntry() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(1), left = 0F, top = 0F, right = 100F, bottom = 250F, current = true)
        )
    }

    /**
     *  (1 - Entry Hall)
     *          |
     *          | to throne
     *          |
     *   (2 - Untitled)
     */
    @Test
    fun render_parentWithSmallerChild_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)

        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2), left = 10F, top = 500F, right = 90F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1), left = 0F, top = 0F, right = 100F, bottom = 250F, current = true)
        )
    }

    /**
     *       (1 - Entry Hall)
     *              |
     *              | to throne
     *              |
     *   (2 - Throne of the king)
     */
    @Test
    fun render_parentWithLargerChild_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
            moveToBookEntry(2)
            setEntryTitle("Throne of the king")

        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2), left = 0F, top = 500F, right = 180F, bottom = 750F, current = true),
            GraphEntry(entry = BookEntry(1), left = 40F, top = 0F, right = 140F, bottom = 250F, current = false)
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

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3), left = 180F, top = 500F, right = 260F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1), left = 80F, top = 0F, right = 180F, bottom = 250F, current = true)
        )
    }

    /**
     *                  (1 - Entry Hall)
     *        to throne |    | to library  | to kitchen
     *         ----------    |             |
     *         |             |             |
     *   (2 - Untitled)  (3 -Untitled)  (4 - Untitled)
     */
    @Test
    fun render_oneParentWithThreeChildren_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            addAction("to kitchen", 4)
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3), left = 180F, top = 500F, right = 260F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(4), left = 360F, top = 500F, right = 440F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1), left = 170F, top = 0F, right = 270F, bottom = 250F, current = true)
        )
    }

    /**
     *                               (1 - Entry Hall)
     *                       to throne |        | to library
     *                        ----------        -----------
     *                        |                           |
     *                    (2 - King)                 (3 -Untitled)
     *         talk to king |    | draw sword
     *         --------------    -----------
     *         |                           |
     *    (4 - Untitled)    (5 - Fight against kings guards)
     */
    @Test
    fun render_graphWithDepth2_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            moveToBookEntry(2)
            setEntryTitle("King")
            addAction("talk to king", 4)
            addAction("draw sword", 5)
            moveToBookEntry(5)
            setEntryTitle("Fight against kings guards")
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(4), left = 0F, top = 1000F, right = 80F, bottom = 1250F, current = false),
            GraphEntry(entry = BookEntry(5), left = 180F, top = 1000F, right = 440F, bottom = 1250F, current = true),
            GraphEntry(entry = BookEntry(2), left = 200F, top = 500F, right = 240F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3), left = 540F, top = 500F, right = 620F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1), left = 360F, top = 0F, right = 460F, bottom = 250F, current = false)
        )
    }

    /**
     *       (1 - Entry Hall)
     *            |  ^
     *  to throne |  | to hall
     *            |  |
     *   (2 - Throne of the king)
     */
    @Test
    fun render_twoEntriesWithCycle_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
            moveToBookEntry(2)
            setEntryTitle("Throne of the king")
            addAction("to hall", 1)
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2), left = 0F, top = 500F, right = 180F, bottom = 750F, current = true),
            GraphEntry(entry = BookEntry(1), left = 40F, top = 0F, right = 140F, bottom = 250F, current = false)
        )
    }

    /**
     *               (1 - Entry Hall)
     *         to throne |        | to library
     *          ----------         ------
     *          |      to library       |
     *   (2 - Throne) -----------> (3 - Untitled)
     */
    @Test
    fun render_threeEntriesWithCycle_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            moveToBookEntry(2)
            setEntryTitle("Throne")
            addAction("to library", 3)
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2), left = 0F, top = 500F, right = 60F, bottom = 750F, current = true),
            GraphEntry(entry = BookEntry(3), left = 160F, top = 500F, right = 240F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1), left = 70F, top = 0F, right = 170F, bottom = 250F, current = false)
        )
    }

    /**
     *  Set up game with the following book:
     *
     *            (Entry Hall)
     *     to throne |   | to library
     *      ----------   -----------
     *      |                     |
     *      |     to library      |
     * (Thorne) ------------->  (Library)
     *      |   <------------     |
     *      |      to throne      |
     *      |                     |
     *      | to king             | to king
     *      |                     |
     *   (King) <------------------
     *
     */
    @Test
    fun render_diamondGraph_renderEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            moveToBookEntry(2)
            setEntryTitle("Throne")
            addAction("to library", 3)
            addAction("to king", 4)
            moveToBookEntry(3)
            setEntryTitle("Library")
            addAction("to throne", 2)
            addAction("to king", 4)
            moveToBookEntry(4)
            setEntryTitle("The King")
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(4, "The King"), left = 0F, top = 1000F, right = 80F, bottom = 1250F, current = true),
            GraphEntry(entry = BookEntry(2, "Throne"), left = 10F, top = 500F, right = 70F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3, "Library"), left = 180F, top = 500F, right = 250F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1, "Entry Hall"), left = 80F, top = 0F, right = 180F, bottom = 250F, current = false)
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
    fun render_renderGraphAddEntryAndRenderGraph_clearDataBeforeEachRendering() {
        // arrange
        val game = Game()
        val underTest = TraversalBookRenderer(game)
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
        }
        underTest.render()
        game.book.addAction("to library", 3)

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3), left = 180F, top = 500F, right = 260F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1), left = 80F, top = 0F, right = 180F, bottom = 250F, current = true)
        )

    }

    /**
     *               (1 - Entry Hall)
     *         to throne |        | to library
     *          ----------         -----------
     *          |                            |
     *   (2 - Untitled)                  (3 - Library with books)
     *                                       |
     *                                       | take one book
     *                                       |
     *                                   (4 - Untitled)
     */
    @Test
    fun render_renderGraphEntryWithDeeperRightBranch_renderGraphEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            moveToBookEntry(3)
            setEntryTitle("Library with books")
            addAction("take one book", 4)
        }
        val underTest = TraversalBookRenderer(game)
        underTest.debug = true

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(entry = BookEntry(2), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(4), left = 230F, top = 1000F, right = 310F, bottom = 1250F, current = false),
            GraphEntry(entry = BookEntry(3), left = 180F, top = 500F, right = 360F, bottom = 750F, current = true),
            GraphEntry(entry = BookEntry(1), left = 130F, top = 0F, right = 230F, bottom = 250F, current = false)
        )
    }

}