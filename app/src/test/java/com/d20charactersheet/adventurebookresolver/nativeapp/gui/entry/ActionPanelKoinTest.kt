package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nhaarman.mockitokotlin2.*
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
    fun `create action panel`() {
        // Arrange
        val actionAddFloatingActionButton: FloatingActionButton = mock()
        val actionMoveRecyclerView: RecyclerView = mock()
        val rootView: View = mock {
            on { findViewById<FloatingActionButton>(R.id.action_add_floating_action_button) } doReturn actionAddFloatingActionButton
            on { findViewById<RecyclerView>(R.id.action_move_recycler_view) } doReturn actionMoveRecyclerView
        }
        val underTest = ActionPanel()
        underTest.itemTouchHelper = mock()

        // Act
        underTest.create(rootView)

        // Assert
        assertActionAddFloatingActionButton(actionAddFloatingActionButton)
        assertActionMoveRecyclerView(actionMoveRecyclerView)
        verify(underTest.itemTouchHelper)?.attachToRecyclerView(actionMoveRecyclerView)
    }

    private fun assertActionAddFloatingActionButton(actionAddFloatingActionButton: FloatingActionButton) {
        argumentCaptor<FloatingActionButtonOnClickListener> {
            verify(actionAddFloatingActionButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(FloatingActionButtonOnClickListener::class.java)
        }
    }

    private fun assertActionMoveRecyclerView(actionMoveRecyclerView: RecyclerView) {
        verify(actionMoveRecyclerView).setHasFixedSize(true)
        argumentCaptor<LinearLayoutManager> {
            verify(actionMoveRecyclerView).layoutManager = capture()
            assertThat(firstValue).isInstanceOf(LinearLayoutManager::class.java)
        }
        argumentCaptor<ActionMoveAdapter> {
            verify(actionMoveRecyclerView).adapter = capture()
            assertThat(firstValue).isInstanceOf(ActionMoveAdapter::class.java)
        }

    }

    @Test
    fun `update action panel`() {
        // Arrange
        val underTest = ActionPanel()
        val actionMoveAdapter: RecyclerView.Adapter<ActionMoveViewHolder> = mock()
        val actionMoveRecyclerView: RecyclerView = mock {
            on { adapter } doReturn actionMoveAdapter
        }
        underTest.actionMoveRecyclerView = actionMoveRecyclerView

        // Act
        underTest.update()

        // Assert
        verify(actionMoveAdapter).notifyDataSetChanged()
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
