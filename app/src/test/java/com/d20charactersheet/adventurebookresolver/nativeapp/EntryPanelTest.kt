package com.d20charactersheet.adventurebookresolver.nativeapp

import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
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
        game.book.note("myNote")

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
            on { getEntryTitle() } doReturn "myTitle"
            on { getEntryId() } doReturn 1
            on { getEntryNote() } doReturn "myNote"
        }
        val underTest = EntryPanel(game)
        underTest.entryTitleEditText = mock()
        underTest.entryIdTextView = mock()
        underTest.entryNoteEditText = mock()

        // Act
        underTest.update()

        // Assert
        verify(underTest.entryTitleEditText).setText("myTitle")
        verify(underTest.entryIdTextView).text = "(1)"
        verify(underTest.entryNoteEditText).setText("myNote")
    }

    @Test
    fun `set entry title on focus lost`() {

        val game = Game()
        game.book = mock()

        // Arrange
        val editable: Editable = mock {
            on { toString() } doReturn "myTitle"
        }
        val editText: EditText = mock {
            on { text } doReturn editable
        }

        // Act
        GameOnFocusChangeListener { text -> game.book.editBookEntry(text) }.onFocusChange(editText, false)

        // Assert
        verify(game.book).editBookEntry("myTitle")
    }

    @Test
    fun `edit entry note on focus lost`() {

        val game = Game()
        game.book = mock()

        // Arrange
        val editable: Editable = mock {
            on { toString() } doReturn "myNote"
        }
        val editText: EditText = mock {
            on { text } doReturn editable
        }

        // Act
        GameOnFocusChangeListener { text -> game.book.note(text) }.onFocusChange(editText, false)

        // Assert
        verify(game.book).note("myNote")
    }

    @Test
    fun `do nothing on focus gained`() {
        // Arrange
        val game = Game()
        game.book = mock()

        // Act
        GameOnFocusChangeListener { text -> game.book.note(text) }.onFocusChange(mock(), true)

        // Assert
        verifyZeroInteractions(game.book)
    }
}
