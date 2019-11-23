package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.TextView
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BookPanelTest {

    @Test
    fun `create book panel`() {

        // Arrange
        val triesValueTextView: TextView = mock()
        val entriesValueTextView: TextView = mock()
        val rootView: View = mock {
            on { findViewById<TextView>(R.id.tries_value_text_view) } doReturn triesValueTextView
            on { findViewById<TextView>(R.id.entries_value_text_view) } doReturn entriesValueTextView
        }
        val underTest = BookPanel(Game())

        // Act
        underTest.create(rootView)

        // Assert
        assertThat(underTest.triesValueTextView).isSameAs(triesValueTextView)
        assertThat(underTest.entriesValueTextView).isSameAs(entriesValueTextView)
    }

    @Test
    fun `update book Panel`() {
        // Arrange
        val underTest = BookPanel(Game())
        underTest.triesValueTextView = mock()
        underTest.entriesValueTextView = mock()

        // Act
        underTest.update()

        // Assert
        verify(underTest.triesValueTextView).text = "1"
        verify(underTest.entriesValueTextView).text = "1 / 400 (0%)"
    }

}