package com.d20charactersheet.adventurebookresolver.nativeapp

import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

internal class ActionPanelKoinTest : KoinTest {

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

        // Act
        ActionPanel(Game()).create(rootView)

        // Assert
        assertActionAddButton(actionAddButton)
        assertActionMoveRecyclerView(actionMoveRecyclerView)
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
        val underTest = ActionPanel(mock())
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
        val underTest = ActionPanel(mock())
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
        val underTest = ActionPanel(mock())
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
        val underTest = ActionPanel(mock())
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

}
