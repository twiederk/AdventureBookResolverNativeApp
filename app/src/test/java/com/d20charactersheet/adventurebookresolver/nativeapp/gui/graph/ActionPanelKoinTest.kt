package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

internal class ActionPanelKoinTest : KoinTest {

    private val game: Game by inject()
    private val graphPanel: GraphPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `ActionMoveAdapter get item count`() {
        // Arrange
        game.book.addAction("myFirstAction", 10)
        game.book.addAction("mySecondAction", 20)

        // Act
        val itemCount = ActionMoveAdapter().itemCount

        // Assert
        assertThat(itemCount).isEqualTo(2)
    }

    @Test
    fun `ActionMoveAdapter bind view holder`() {
        // Arrange
        game.book.addAction("myFirstAction", 10)
        game.book.addAction("mySecondAction", 20)

        val actionMoveViewHolder: ActionMoveViewHolder = mock()

        // Act
        ActionMoveAdapter()
            .onBindViewHolder(actionMoveViewHolder, 0)

        // Assert
        verify(actionMoveViewHolder).setAction("myFirstAction", 10)
    }

    @Test
    fun `ActionMoveAdapter delete item`() {
        // Arrange
        declareMock<Game> {
            whenever(getAction(0)).doReturn(Action("myActionToDelete", BookEntry(1), BookEntry(10)))
        }
        declareMock<GraphPanel>()

        // Act
        ActionMoveAdapter().deleteItem(0)

        // Assert
        verify(game).delete(10)
        verify(graphPanel).update()
    }

    @Test
    fun `ActionDeleteSimpleCallback deletes action`() {
        // Arrange
        val actionMoveAdapter: ActionMoveAdapter = mock()
        val viewHolder: RecyclerView.ViewHolder = mock {
            on { adapterPosition } doReturn 10
        }

        // Act
        ActionDeleteOnSwipeListener(
            actionMoveAdapter
        ).onSwiped(viewHolder, 0)

        // Assert
        verify(actionMoveAdapter).deleteItem(10)
        verify(actionMoveAdapter).notifyItemRemoved(10)
    }
}
