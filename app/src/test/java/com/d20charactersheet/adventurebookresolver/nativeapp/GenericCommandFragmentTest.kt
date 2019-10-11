package com.d20charactersheet.adventurebookresolver.nativeapp

import android.text.Editable
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GenericCommandFragmentTest {

    private val underTest = GenericCommandFragment()

    @Test
    fun `execute button onClick executes command`() {
        // Arrange

        val mainActivity = mock<MainActivity>()
        val genericCommandFragment = mock<GenericCommandFragment> {
            on { getSelectedCommand() } doReturn Command.Create
            on { getArgument() } doReturn "myArgs"
            on { activity } doReturn mainActivity
        }
        val game = mock<Game> {
            on { createBook("myArgs") } doReturn "book created"
        }
        val underTest = ExecuteOnClickListener(genericCommandFragment, game)

        // Act
        underTest.onClick(mock())

        // Assert
        verify(game).createBook("myArgs")
        verify(genericCommandFragment).appendOutput("book created")
        verify(mainActivity).update()
    }

    @Test
    fun `clear button onClick clears output area`() {
        // Arrange
        val genericCommandFragment = mock<GenericCommandFragment>()
        val underTest = ClearOnClickListener(genericCommandFragment)

        // Act
        underTest.onClick(mock())

        // Assert
        verify(genericCommandFragment).clearOutput()
    }

    @Test
    fun `get selected command`() {
        // Arrange
        underTest.commandSpinner = mock {
            on { selectedItem } doReturn Command.Create
        }

        // Act
        val selectedCommand = underTest.getSelectedCommand()

        // Assert
        assertThat(selectedCommand).isSameAs(Command.Create)
    }

    @Test
    fun `get argument`() {
        // Arrange
        val editable = mock<Editable> {
            on { toString() } doReturn "myArgument"
        }
        underTest.argumentEditText = mock {
            on { text } doReturn editable
        }

        // Act
        val argument = underTest.getArgument()

        // Assert
        assertThat(argument).isEqualTo("myArgument")
    }

    @Test
    fun `append output`() {
        // Arrange
        underTest.outputTextView = mock()

        // Act
        underTest.appendOutput("myOutput")

        // Assert
        verify(underTest.outputTextView).append("myOutput\n")
    }

    @Test
    fun `clear output`() {
        // Arrange
        underTest.outputTextView = mock()

        // Act
        underTest.clearOutput()

        // Assert
        verify(underTest.outputTextView).text = ""
    }

}