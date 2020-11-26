package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

class ActionMoveViewHolderKoinTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
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
        val actionColor: ActionColor = mock {
            on { getColor(rootView, WayMark.NORMAL, Visit.VISITED) } doReturn 1000
        }
        whenever(game.getEntryFromNextEntries(10)).doReturn(BookEntry(id = 10, visit = Visit.VISITED))

        // Act
        ActionMoveViewHolder(
            rootView,
            actionColor
        ).setAction("myActionLabel", 10)

        // Assert
        verify(actionMoveLabelTextView).text = "myActionLabel"
        verify(actionMoveEntryIdButton).text = "10"
        verify(rootView).setBackgroundColor(1000)
    }

}