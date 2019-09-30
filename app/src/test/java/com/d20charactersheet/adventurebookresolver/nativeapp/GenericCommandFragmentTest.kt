package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.nhaarman.mockitokotlin2.*
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

    @Test
    fun onCreate() {
        // Arrange
        val container = mock<ViewGroup>()
        val commandSpinner = mock<Spinner>()
        val argumentEditText = mock<EditText>()
        val executeButton = mock<Button>()
        val clearButton = mock<Button>()
        val outputTextView = mock<TextView>()
        val rootView = mock<View> {
            on { findViewById<Spinner>(R.id.command_spinner) } doReturn commandSpinner
            on { findViewById<EditText>(R.id.argument_edit_text) } doReturn argumentEditText
            on { findViewById<Button>(R.id.execute_button) } doReturn executeButton
            on { findViewById<Button>(R.id.clear_button) } doReturn clearButton
            on { findViewById<TextView>(R.id.output_text_view) } doReturn outputTextView
        }
        val inflater = mock<LayoutInflater> {
            on { inflate(R.layout.fragment_generic_command, container, false) } doReturn rootView
        }
        val savedInstanceState = mock<Bundle>()

        // Act
        underTest.onCreateView(inflater, container, savedInstanceState)

        // Assert
        verify(inflater).inflate(R.layout.fragment_generic_command, container, false)
        verify(commandSpinner).adapter = any()
        argumentCaptor<ExecuteOnClickListener> {
            verify(executeButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(ExecuteOnClickListener::class.java)
        }
        argumentCaptor<ClearOnClickListener> {
            verify(clearButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(ClearOnClickListener::class.java)
        }

    }

}