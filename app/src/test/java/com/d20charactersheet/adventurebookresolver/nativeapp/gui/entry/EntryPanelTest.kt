package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EntryPanelTest {

    @Test
    fun `create entry panel`() {
        // Arrange
        val entryTitleEditText: EditText = mock()
        val entryIdTextView: TextView = mock()
        val entryNoteEditText: EditText = mock()
        val rootView: View = mock {
            on { findViewById<TextView>(R.id.entry_title_edit_text) } doReturn entryTitleEditText
            on { findViewById<TextView>(R.id.entry_id_text_view) } doReturn entryIdTextView
            on { findViewById<TextView>(R.id.entry_note_edit_text) } doReturn entryNoteEditText
        }
        val game = Game()
        game.book.setEntryNote("myEntryNote")

        // Act
        EntryPanel(game).create(rootView)

        // Assert
        argumentCaptor<GameOnFocusChangeListener> {
            verify(entryTitleEditText).onFocusChangeListener = capture()
            assertThat(firstValue).isInstanceOf(GameOnFocusChangeListener::class.java)
        }
        argumentCaptor<GameOnFocusChangeListener> {
            verify(entryNoteEditText).onFocusChangeListener = capture()
            assertThat(firstValue).isInstanceOf(GameOnFocusChangeListener::class.java)
        }
    }

    @Test
    fun `update entry panel`() {
        // Arrange
        val game = Game()
        game.book = mock {
            on { getEntryTitle() } doReturn "myEntryTitle"
            on { getEntryId() } doReturn 1
            on { getEntryNote() } doReturn "myEntryNote"
        }
        val underTest =
            EntryPanel(game)
        underTest.entryTitleEditText = mock()
        underTest.entryIdTextView = mock()
        underTest.entryNoteEditText = mock()

        // Act
        underTest.update()

        // Assert
        verify(underTest.entryTitleEditText).setText("myEntryTitle")
        verify(underTest.entryIdTextView).text = "(1)"
        verify(underTest.entryNoteEditText).setText("myEntryNote")
    }

    @Test
    fun `set entry title on focus lost`() {

        val game = Game()
        game.book = mock()

        // Arrange
        val editable: Editable = mock {
            on { toString() } doReturn "myEntryTitle"
        }
        val editText: EditText = mock {
            on { text } doReturn editable
        }

        // Act
        GameOnFocusChangeListener { text ->
            game.book.setEntryTitle(
                text
            )
        }.onFocusChange(editText, false)

        // Assert
        verify(game.book).setEntryTitle("myEntryTitle")
    }

    @Test
    fun `edit entry note on focus lost`() {

        val game = Game()
        game.book = mock()

        // Arrange
        val editable: Editable = mock {
            on { toString() } doReturn "myEntryNote"
        }
        val editText: EditText = mock {
            on { text } doReturn editable
        }

        // Act
        GameOnFocusChangeListener { text ->
            game.book.setEntryNote(
                text
            )
        }.onFocusChange(editText, false)

        // Assert
        verify(game.book).setEntryNote("myEntryNote")
    }

    @Test
    fun `do nothing on focus gained`() {
        // Arrange
        val game = Game()
        game.book = mock()

        // Act
        GameOnFocusChangeListener { text ->
            game.book.setEntryNote(
                text
            )
        }.onFocusChange(mock(), true)

        // Assert
        verifyZeroInteractions(game.book)
    }
}
