package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class ActionMoveViewHolderKoinTest : KoinTest {

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
    fun `create ActionMoveViewHolder with ActionMoveOnClickListener`() {
        // Arrange
        val moveActionEntryIdButton: Button = mock()
        val rootView: View = mock {
            on { findViewById<TextView>(R.id.move_action_label_text_view) } doReturn mock()
            on { findViewById<Button>(R.id.move_action_entry_id_button) } doReturn moveActionEntryIdButton
        }

        // Act
        ActionMoveViewHolder(rootView)

        // Assert
        argumentCaptor<ActionMoveOnClickListener> {
            verify(moveActionEntryIdButton).setOnClickListener(capture())
            Assertions.assertThat(firstValue).isInstanceOf(ActionMoveOnClickListener::class.java)
        }
    }

    @Test
    fun `ActionMoveViewHolder setAction`() {
        // Arrange
        val actionMoveLabelTextView: TextView = mock()
        val actionMoveEntryIdButton: Button = mock()
        val rootView: View = mock {
            on { findViewById<TextView>(R.id.move_action_label_text_view) } doReturn actionMoveLabelTextView
            on { findViewById<Button>(R.id.move_action_entry_id_button) } doReturn actionMoveEntryIdButton
        }

        // Act
        ActionMoveViewHolder(rootView).setAction("myActionLabel", 10)

        // Assert
        verify(actionMoveLabelTextView).text = "myActionLabel"
        verify(actionMoveEntryIdButton).text = "10"
    }


}