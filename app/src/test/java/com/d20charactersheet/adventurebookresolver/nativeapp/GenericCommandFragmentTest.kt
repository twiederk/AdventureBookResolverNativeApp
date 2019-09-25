package com.d20charactersheet.adventurebookresolver.nativeapp

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class GenericCommandFragmentTest {

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
}