package com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class BookPanelTest {

    @Test
    fun `create book panel`() {

        // Arrange
        val triesValueTextView: TextView = mock()
        val entriesValueTextView: TextView = mock()
        val bookNoteEditText: EditText = mock()
        val rootView: View = mock {
            on { findViewById<TextView>(R.id.tries_value_text_view) } doReturn triesValueTextView
            on { findViewById<TextView>(R.id.entries_value_text_view) } doReturn entriesValueTextView
            on { findViewById<EditText>(R.id.book_note_edit_text) } doReturn bookNoteEditText
        }
        val underTest =
            BookPanel(
                Game()
            )

        // Act
        underTest.create(rootView)

        // Assert
        assertThat(underTest.triesValueTextView).isSameAs(triesValueTextView)
        assertThat(underTest.entriesValueTextView).isSameAs(entriesValueTextView)
        argumentCaptor<BookOnFocusChangeListener> {
            verify(bookNoteEditText).onFocusChangeListener = capture()
            assertThat(firstValue).isInstanceOf(BookOnFocusChangeListener::class.java)
        }
    }

    @Test
    fun `update book Panel`() {
        // Arrange
        val game = Game()
        game.book.note = "myBookNote"
        val underTest =
            BookPanel(
                game
            )
        underTest.triesValueTextView = mock()
        underTest.entriesValueTextView = mock()
        underTest.bookNoteEditText = mock()

        // Act
        underTest.update()

        // Assert
        verify(underTest.triesValueTextView).text = "1"
        verify(underTest.entriesValueTextView).text = "1 / 400 (0%)"
        verify(underTest.bookNoteEditText).setText("myBookNote")
    }

}