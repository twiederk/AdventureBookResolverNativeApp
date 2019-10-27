package com.d20charactersheet.adventurebookresolver.nativeapp

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
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
    private val bookPanel: BookPanel by inject()

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
        val actionLabelEditText: EditText = mock()
        val actionIdEditText: EditText = mock()
        val actionAddButton: Button = mock()
        val actionMoveRecyclerView: RecyclerView = mock()
        val rootView: View = mock {
            on { findViewById<EditText>(R.id.action_label_edit_text) } doReturn actionLabelEditText
            on { findViewById<EditText>(R.id.action_id_edit_text) } doReturn actionIdEditText
            on { findViewById<Button>(R.id.action_add_button) } doReturn actionAddButton
            on { findViewById<RecyclerView>(R.id.action_move_recycler_view) } doReturn actionMoveRecyclerView
        }
        val underTest = ActionPanel()
        underTest.itemTouchHelper = mock()

        // Act
        underTest.create(rootView)

        // Assert
        assertActionAddButton(actionAddButton)
        assertActionMoveRecyclerView(actionMoveRecyclerView)
        verify(underTest.itemTouchHelper)?.attachToRecyclerView(actionMoveRecyclerView)
    }

    private fun assertActionAddButton(actionAddButton: Button) {
        argumentCaptor<ActionAddOnClickListener> {
            verify(actionAddButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(ActionAddOnClickListener::class.java)
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
    fun `get action label`() {
        // Arrange
        val underTest = ActionPanel()
        val editable = mock<Editable> {
            on { toString() } doReturn "myActionLabel"
        }
        underTest.actionLabelEditText = mock {
            on { text } doReturn editable
        }

        // Act
        val actionLabel = underTest.getActionLabel()

        // Assert
        assertThat(actionLabel).isEqualTo("myActionLabel")
    }

    @Test
    fun `get action id`() {
        // Arrange
        val underTest = ActionPanel()
        val editable = mock<Editable> {
            on { toString() } doReturn "10"
        }
        underTest.actionIdEditText = mock {
            on { text } doReturn editable
        }

        // Act
        val actionId = underTest.getActionId()

        // Assert
        assertThat(actionId).isEqualTo("10")
    }

    @Test
    fun `clear action panel`() {
        // Arrange
        val underTest = ActionPanel()
        val actionLabelEditText: EditText = mock()
        val actionIdEditText: EditText = mock()
        underTest.actionLabelEditText = actionLabelEditText
        underTest.actionIdEditText = actionIdEditText

        // Act
        underTest.clear()

        // Assert
        verify(actionLabelEditText).setText("")
        verify(actionIdEditText).setText("")
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
        ActionMoveAdapter().onBindViewHolder(actionMoveViewHolder, 0)

        // Assert
        verify(actionMoveViewHolder).setAction("myFirstAction", 10)
    }

    @Test
    fun `ActionMoveAdapter delete item`() {
        // Arrange
        declareMock<Game> {
            given(this.getAction(0)).willReturn(Action("myActionToDelete", BookEntry(1), BookEntry(10)))
        }
        declareMock<BookPanel>()

        // Act
        ActionMoveAdapter().deleteItem(0)

        // Assert
        verify(game).delete(10)
        verify(bookPanel).update()
    }

    @Test
    fun `ActionDeleteSimpleCallback deletes action`() {
        // Arrange
        val actionMoveAdapter: ActionMoveAdapter = mock()
        val viewHolder: RecyclerView.ViewHolder = mock {
            on { adapterPosition } doReturn 10
        }

        // Act
        ActionDeleteSimpleCallback(actionMoveAdapter).onSwiped(viewHolder, 0)

        // Assert
        verify(actionMoveAdapter).deleteItem(10)
        verify(actionMoveAdapter).notifyItemRemoved(10)
    }

    @Test
    fun `ActionDeleteSimpleCallback move does nothing`() {
        // Act
        val result = ActionDeleteSimpleCallback(mock()).onMove(mock(), mock(), mock())

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun `ActionDeleteSimpleCallback onChildDraw swipe right`() {
        // Arrange
        val canvas: Canvas = mock()
        val itemView: View = mock {
            on { left } doReturn 10
            on { top } doReturn 20
            on { right } doReturn 30
            on { bottom } doReturn 40
        }
        val underTest = ActionDeleteSimpleCallback(mock())
        val background: Drawable = mock()
        underTest.background = background

        // Act
        underTest.onChildDraw(canvas, itemView, 10)

        // Assert
        verify(background).setBounds(10, 20, 40, 40)
        verify(background).draw(canvas)
        verifyNoMoreInteractions(background)
    }

    @Test
    fun `ActionDeleteSimpleCallback onChildDraw swipe left`() {
        // Arrange
        val canvas: Canvas = mock()
        val itemView: View = mock {
            on { left } doReturn 10
            on { top } doReturn 20
            on { right } doReturn 30
            on { bottom } doReturn 40
        }
        val underTest = ActionDeleteSimpleCallback(mock())
        val background: Drawable = mock()
        underTest.background = background

        // Act
        underTest.onChildDraw(canvas, itemView, -10)

        // Assert
        verify(background).setBounds(0, 20, 30, 40)
        verify(background).draw(canvas)
        verifyNoMoreInteractions(background)
    }

    @Test
    fun `ActionDeleteSimpleCallback onChildDraw swipe stopped`() {
        // Arrange
        val canvas: Canvas = mock()
        val itemView: View = mock()
        val underTest = ActionDeleteSimpleCallback(mock())
        val background: Drawable = mock()
        underTest.background = background

        // Act
        underTest.onChildDraw(canvas, itemView, 0)

        // Assert
        verify(background).setBounds(0, 0, 0, 0)
        verify(background).draw(canvas)
        verifyNoMoreInteractions(background)
    }
}
