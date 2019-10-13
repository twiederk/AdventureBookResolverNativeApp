package com.d20charactersheet.adventurebookresolver.nativeapp

import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions
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
        verify(entryTitleEditText).setText("Untitled")
        argumentCaptor<GameTextWatcher> {
            verify(entryTitleEditText).addTextChangedListener(capture())
            Assertions.assertThat(firstValue).isInstanceOf(GameTextWatcher::class.java)
        }
        verify(entryIdTextView).text = "(1)"
        verify(entryNoteEditText).setText("myNote")
        argumentCaptor<GameTextWatcher> {
            verify(entryNoteEditText).addTextChangedListener(capture())
            Assertions.assertThat(firstValue).isInstanceOf(GameTextWatcher::class.java)
        }
    }

    @Test
    fun `set entry title`() {

        val game = Game()
        game.book = mock()

        // Arrange
        val editable: Editable = mock {
            on { toString() } doReturn "myTitle"
        }

        // Act
        GameTextWatcher { text -> game.book.editBookEntry(text) }.afterTextChanged(editable)

        // Assert
        verify(game.book).editBookEntry("myTitle")
    }

    @Test
    fun `edit entry note`() {

        val game = Game()
        game.book = mock()

        // Arrange
        val editable: Editable = mock {
            on { toString() } doReturn "myNote"
        }

        // Act
        GameTextWatcher { text -> game.book.note(text) }.afterTextChanged(editable)

        // Assert
        verify(game.book).note("myNote")
    }

}
