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
     *  GRAPH 1
     *  =======
     *
     *  (1 - Entry Hall)
     */
    @Test
    fun render_graph1_rootEntryOnly_renderRootEntry() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 0F, top = 0F, right = 100F, bottom = 250F, current = true)
        )
    }

    /**
     * GRAPH 2
     * =======
     *
     *  (1 - Entry Hall)
     *          |
     *          | to throne
     *          |
     *   (2 - Untitled)
     */
    @Test
    fun render_graph2_parentWithSmallerChild_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry Hall")
            addAction("to throne", 2)

        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2, "Untitled"), left = 10F, top = 500F, right = 90F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 0F, top = 0F, right = 100F, bottom = 250F, current = true)
        )
    }

    /**
     * GRAPH 2
     * =======
     *
     *       (1 - Entry Hall)
     *              |
     *              | to throne
     *              |
     *   (2 - Throne of the king)
     */
    @Test
    fun render_graph2_parentWithLargerChild_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to throne", 2)
            moveToBookEntry(2)
            setEntryTitle("throne of the king")

        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2, "throne of the king"), left = 0F, top = 500F, right = 180F, bottom = 750F, current = true),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 40F, top = 0F, right = 140F, bottom = 250F, current = false)
        )
    }

    /**
     * GRAPH 3
     * =======
     *
     *               (1 - Entry Hall)
     *         to throne |        | to library
     *          ----------         -----------
     *          |                            |
     *   (2 - Untitled)                  (3 - Untitled)
     */
    @Test
    fun render_graph3_oneParentWithTwoChildren_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to throne", 2)
            addAction("to library", 3)
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2, "Untitled"), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3, "Untitled"), left = 180F, top = 500F, right = 260F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 80F, top = 0F, right = 180F, bottom = 250F, current = true)
        )
    }

    /**
     * GRAPH 4
     * =======
     *                  (1 - Entry Hall)
     *        to throne |    | to library  | to kitchen
     *         ----------    |             |
     *         |             |             |
     *   (2 - Untitled)  (3 -Untitled)  (4 - Untitled)
     */
    @Test
    fun render_graph4_oneParentWithThreeChildren_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            addAction("to kitchen", 4)
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2, "Untitled"), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3, "Untitled"), left = 180F, top = 500F, right = 260F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(4, "Untitled"), left = 360F, top = 500F, right = 440F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 170F, top = 0F, right = 270F, bottom = 250F, current = true)
        )
    }

    /**
     * GRAPH 5
     * =======
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
    fun render_graph5_graphWithDepth2_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            moveToBookEntry(2)
            setEntryTitle("king")
            addAction("talk to king", 4)
            addAction("draw sword", 5)
            moveToBookEntry(5)
            setEntryTitle("fight against kings guards")
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(4, "Untitled"), left = 0F, top = 1000F, right = 80F, bottom = 1250F, current = false),
            GraphEntry(entry = BookEntry(5, "fight against kings guards"), left = 180F, top = 1000F, right = 440F, bottom = 1250F, current = true),
            GraphEntry(entry = BookEntry(2, "Untitled"), left = 200F, top = 500F, right = 240F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3, "king"), left = 540F, top = 500F, right = 620F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 360F, top = 0F, right = 460F, bottom = 250F, current = false)
        )
    }

    /**
     * GRAPH 2
     * =======
     *
     *       (1 - Entry Hall)
     *            |  ^
     *  to throne |  | to hall
     *            |  |
     *   (2 - Throne of the king)
     */
    @Test
    fun render_graph2_twoEntriesWithCycle_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to throne", 2)
            moveToBookEntry(2)
            setEntryTitle("throne of the king")
            addAction("to hall", 1)
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2, "throne of the king"), left = 0F, top = 500F, right = 180F, bottom = 750F, current = true),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 40F, top = 0F, right = 140F, bottom = 250F, current = false)
        )
    }

    /**
     * GRAPH 3
     * =======
     *
     *               (1 - Entry Hall)
     *         to throne |        | to library
     *          ----------         ------
     *          |      to library       |
     *   (2 - Throne) -----------> (3 - Untitled)
     */
    @Test
    fun render_graph3_threeEntriesWithCycle_renderBookEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            moveToBookEntry(2)
            setEntryTitle("throne")
            addAction("to library", 3)
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2, "throne"), left = 0F, top = 500F, right = 60F, bottom = 750F, current = true),
            GraphEntry(entry = BookEntry(3, "Untitled"), left = 160F, top = 500F, right = 240F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 70F, top = 0F, right = 170F, bottom = 250F, current = false)
        )
    }

    /**
     * GRAPH 6 - Diamond
     *
     * Set up game with the following book:
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
    fun render_graph6_diamondGraph_renderEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            moveToBookEntry(2)
            setEntryTitle("throne")
            addAction("to library", 3)
            addAction("to king", 4)
            moveToBookEntry(3)
            setEntryTitle("library")
            addAction("to throne", 2)
            addAction("to king", 4)
            moveToBookEntry(4)
            setEntryTitle("the king")
        }

        // act
        val (entries, _) = TraversalBookRenderer(game).render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(4, "the king"), left = 0F, top = 1000F, right = 80F, bottom = 1250F, current = true),
            GraphEntry(entry = BookEntry(2, "throne"), left = 10F, top = 500F, right = 70F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3, "library"), left = 180F, top = 500F, right = 250F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 80F, top = 0F, right = 180F, bottom = 250F, current = false)
        )
    }

    /**
     * GRAPH 3
     * =======
     *
     *               (1 - Entry Hall)
     *         to throne |        | to library
     *          ----------         -----------
     *          |                            |
     *   (2 - Untitled)                  (3 - Untitled)
     */
    @Test
    fun render_graph3_renderGraphAddEntryAndRenderGraph_clearDataBeforeEachRendering() {
        // arrange
        val game = Game()
        val underTest = TraversalBookRenderer(game)
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to throne", 2)
        }
        underTest.render()
        game.book.addAction("to library", 3)

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).containsExactly(
            GraphEntry(entry = BookEntry(2, "Untitled"), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(3, "Untitled"), left = 180F, top = 500F, right = 260F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 80F, top = 0F, right = 180F, bottom = 250F, current = true)
        )

    }

    /**
     * GRAPH 7
     * =======
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
    fun render_graph7_renderGraphEntryWithDeeperRightBranch_renderGraphEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to throne", 2)
            addAction("to library", 3)
            moveToBookEntry(3)
            setEntryTitle("library with books")
            addAction("take one book", 4)
        }
        val underTest = TraversalBookRenderer(game)

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(entry = BookEntry(2, "Untitled"), left = 0F, top = 500F, right = 80F, bottom = 750F, current = false),
            GraphEntry(entry = BookEntry(4, "Untitled"), left = 230F, top = 1000F, right = 310F, bottom = 1250F, current = false),
            GraphEntry(entry = BookEntry(3, "library with books"), left = 180F, top = 500F, right = 360F, bottom = 750F, current = true),
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 130F, top = 0F, right = 230F, bottom = 250F, current = false)
        )
    }

    /**
     * GRAPH 8
     * =======
     *
     *               (1 - Entry Hall)
     *         to city |        | to throne
     *          --------         -----------
     *          |                          |
     *      (10 - City)               (20 - throne)
     *                                     |
     *                                     | pass guards
     *                                     |
     *                           (50 - passed the guards)
     *                                     |
     *                                     | to king
     *                                     |
     *              (100 - great king of britannia with a very long text)
     */
    @Test
    fun render_graph8_renderGraphEntryWithChildrenBelowLeaf_renderGraphEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to city", 10)
            addAction("to throne", 20)
            moveToBookEntry(10)
            setEntryTitle("city")
            restart()
            moveToBookEntry(20)
            setEntryTitle("throne")
            addAction("pass guards", 50)
            moveToBookEntry(50)
            setEntryTitle("passed the guards")
            addAction("to king", 100)
            moveToBookEntry(100)
            setEntryTitle("great king of britannia with a very long text")
        }
        val underTest = TraversalBookRenderer(game)

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(entry = BookEntry(1, "entry hall"), left = 77.5F, top = 0F, right = 177.5F, bottom = 250F),
            GraphEntry(entry = BookEntry(10, "city"), left = 0F, top = 500F, right = 40F, bottom = 750F),
            GraphEntry(entry = BookEntry(20, "throne"), left = 195F, top = 500F, right = 255F, bottom = 750F),
            GraphEntry(entry = BookEntry(50, "passed the guards"), left = 140F, top = 1000F, right = 310F, bottom = 1250F),
            GraphEntry(
                entry = BookEntry(100, "great king of britannia with a very long text"),
                left = 0F,
                top = 1500F,
                right = 450F,
                bottom = 1750F,
                current = true
            )
        )
    }

    /**
     * GRAPH 9
     * =======
     *
     *               (1 - Entry Hall)
     *         to city |        | to throne
     *          --------         --------------------------------
     *          |                                               |
     *      (10 - City)                               (20 - throne room)
     *          |                            talk             |  | fight
     *          | to market                |------------------   --------|
     *          |                          |                             |
     *  (100 Market place)     (100 - The king of britannia)    (300) The Guards
     *
     */
    @Test
    fun render_graph9_renderGraphWithMoreChildrenToTheRight_renderGraphEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("entry hall")
            addAction("to city", 10)
            addAction("to throne", 20)
            moveToBookEntry(10)
            setEntryTitle("city")
            addAction("to market", 100)
            moveToBookEntry(100)
            setEntryTitle("market place")
            restart()
            moveToBookEntry(20)
            setEntryTitle("throne room")
            addAction("talk", 200)
            addAction("fight", 300)
            moveToBookEntry(200)
            setEntryTitle("the king of britannia")
            restart()
            moveToBookEntry(20)
            moveToBookEntry(300)
            setEntryTitle("the Guards")
        }
        val underTest = TraversalBookRenderer(game)

        // act
        val (entries, _) = underTest.render()

        // assert
        assertThat(entries).contains(
            GraphEntry(entry = BookEntry(id = 100, title = "market place"), left = 0F, top = 1000F, right = 120F, bottom = 1250F),
            GraphEntry(entry = BookEntry(id = 10, title = "city"), left = 40F, top = 500F, right = 80F, bottom = 750F),
            GraphEntry(entry = BookEntry(id = 200, title = "the king of britannia"), left = 220F, top = 1000F, right = 430F, bottom = 1250F),
            GraphEntry(entry = BookEntry(id = 300, title = "the guard"), left = 530F, top = 1000F, right = 630F, bottom = 1250F, current = true),
            GraphEntry(entry = BookEntry(id = 20, title = "throne room"), left = 370F, top = 500F, right = 480F, bottom = 750F),
            GraphEntry(entry = BookEntry(id = 1, title = "entry hall"), left = 210F, top = 0F, right = 310F, bottom = 250F),
        )
    }

}